package com.adarrivi.factory.problem;

import org.encog.ml.genetic.BasicGeneticAlgorithm;
import org.encog.ml.genetic.GeneticAlgorithm;
import org.encog.ml.genetic.crossover.SpliceNoRepeat;
import org.encog.ml.genetic.genome.Genome;
import org.encog.ml.genetic.mutate.MutateShuffle;
import org.encog.ml.genetic.population.BasicPopulation;
import org.encog.ml.genetic.population.Population;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adarrivi.factory.genetics.PlanningGenome;
import com.adarrivi.factory.planning.Planning;

public class GeneticPlanningSolver {

    private Logger LOG = LoggerFactory.getLogger(GeneticPlanningSolver.class);

    private PlanningProblemProperties problemProperties;
    private GeneticAlgorithm geneticAlgorithm;
    private Planning bestPlanning;

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
            genome.createRandomGenome(problemProperties);
            geneticAlgorithm.getPopulation().add(genome);
            geneticAlgorithm.calculateScore(genome);
        }
        population.claim(geneticAlgorithm);
        population.sort();
    }

    public Planning solve() {
        int sameSolutionCount = 0;
        double lastSolution = Double.MAX_VALUE;
        int iteration = 0;
        while (sameSolutionCount < problemProperties.getMaxSameSolution()) {
            geneticAlgorithm.iteration();
            Genome bestGenome = geneticAlgorithm.getPopulation().getBest();
            double bestScore = bestGenome.getScore();
            if (Math.abs(lastSolution - bestScore) < 1.0) {
                sameSolutionCount++;
            } else {
                sameSolutionCount = 0;
            }
            iteration++;
            lastSolution = bestScore;
            LOG.debug("Iteration {}, best score {}", iteration, bestScore);
        }
        Genome bestGenome = geneticAlgorithm.getPopulation().getBest();
        bestPlanning = (Planning) bestGenome.getOrganism();
        displaySolution();
        return bestPlanning;
    }

    private void displaySolution() {
        Genome bestGenome = geneticAlgorithm.getPopulation().getBest();
        LOG.debug("Best planning found with score {}: \n{}", bestGenome.getScore(), bestPlanning);
    }
}
