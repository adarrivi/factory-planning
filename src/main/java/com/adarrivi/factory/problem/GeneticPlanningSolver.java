package com.adarrivi.factory.problem;

import org.encog.ml.genetic.BasicGeneticAlgorithm;
import org.encog.ml.genetic.GeneticAlgorithm;
import org.encog.ml.genetic.crossover.SpliceNoRepeat;
import org.encog.ml.genetic.genes.Gene;
import org.encog.ml.genetic.genes.IntegerGene;
import org.encog.ml.genetic.genome.Chromosome;
import org.encog.ml.genetic.genome.Genome;
import org.encog.ml.genetic.mutate.MutateShuffle;
import org.encog.ml.genetic.population.BasicPopulation;
import org.encog.ml.genetic.population.Population;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adarrivi.factory.genetics.PlanningGenome;
import com.adarrivi.factory.planning.Auditor;
import com.adarrivi.factory.planning.Planning;

public class GeneticPlanningSolver {

    private Logger LOG = LoggerFactory.getLogger(GeneticPlanningSolver.class);

    private PlanningProblemProperties problemProperties;
    private GeneticAlgorithm geneticAlgorithm;

    public GeneticPlanningSolver(PlanningProblemProperties problemProperties) {
        this.problemProperties = problemProperties;
    }

    public void init() {
        geneticAlgorithm = new BasicGeneticAlgorithm();
        Planning planning = problemProperties.getPlanning();
        geneticAlgorithm.setCalculateScore(planning);
        geneticAlgorithm.setMutationPercent(problemProperties.getMutationPercent());
        geneticAlgorithm.setPercentToMate(problemProperties.getPercentToMate());
        geneticAlgorithm.setMatingPopulation(problemProperties.getMatingPopulationPercent());
        geneticAlgorithm.setCrossover(new SpliceNoRepeat(planning.getPlanningSize() / problemProperties.getCrossoverSlices()));
        geneticAlgorithm.setMutate(new MutateShuffle());
        setRandomInitialPopulation();
    }

    private void setRandomInitialPopulation() {
        int initalPopulationSize = problemProperties.getPopulationSize();
        Population population = new BasicPopulation(initalPopulationSize);
        geneticAlgorithm.setPopulation(population);

        for (int i = 0; i < initalPopulationSize; i++) {
            PlanningGenome genome = new PlanningGenome();
            genome.createRandomGenome(problemProperties.getPlanning());
            geneticAlgorithm.getPopulation().add(genome);
            geneticAlgorithm.calculateScore(genome);
        }
        population.claim(geneticAlgorithm);
        population.sort();
    }

    public void solve() {
        int sameSolutionCount = 0;
        int iteration = 1;
        double lastSolution = Double.MAX_VALUE;
        while (sameSolutionCount < problemProperties.getMaxSameSolution()) {
            geneticAlgorithm.iteration();
            Genome bestGenome = geneticAlgorithm.getPopulation().getBest();
            Auditor auditor = new Auditor((Planning) bestGenome.getOrganism(), bestGenome.getScore(), iteration++);
            if (Math.abs(lastSolution - auditor.getScore()) < 1.0) {
                sameSolutionCount++;
            } else {
                sameSolutionCount = 0;
            }

            lastSolution = auditor.getScore();
        }
        LOG.debug("Good solution found:");
        displaySolution();
    }

    private void displaySolution() {
        boolean first = true;
        Chromosome bestChromosome = geneticAlgorithm.getPopulation().getBest().getChromosomes().get(0);
        StringBuffer sb = new StringBuffer();
        for (Gene gene : bestChromosome.getGenes()) {
            if (!first) {
                sb.append(">");
            }
            sb.append(((IntegerGene) gene).getValue());
            first = false;
        }
        LOG.debug(sb.toString());
    }
}
