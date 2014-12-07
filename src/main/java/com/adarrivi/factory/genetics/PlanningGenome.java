package com.adarrivi.factory.genetics;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.encog.ml.genetic.genome.BasicGenome;
import org.encog.ml.genetic.genome.Chromosome;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.SensiblePlanningRandomizer;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.problem.PlanningProblemProperties;

public class PlanningGenome extends BasicGenome {

    private static final long serialVersionUID = 1L;

    public PlanningGenome() {
        getChromosomes().add(new Chromosome());
    }

    // This method should be called only once
    public void createRandomGenome(PlanningProblemProperties problem) {
        createRandomPlanning(problem);
        encode();
    }

    private Chromosome getChromosome() {
        return getChromosomes().get(0);
    }

    private void createRandomPlanning(PlanningProblemProperties problem) {
        SensiblePlanningRandomizer randomizer = new SensiblePlanningRandomizer(problem);
        setOrganism(randomizer.getRandomizedPlaning());
    }

    @Override
    public void decode() {
        Planning planning = (Planning) getOrganism();
        Chromosome planningChromosome = getChromosome();
        for (int i = 0; i < planningChromosome.size(); i++) {
            PlanningGene gene = (PlanningGene) planningChromosome.get(i);
            planning.setNewShift(gene.getWorkerName(), gene.getDay(), gene.getLine(), gene.getShiftType());
        }
        setOrganism(planning);
    }

    @Override
    public void encode() {
        Planning planning = (Planning) getOrganism();
        Stream<PlanningGene> genes = planning.getAllWorkers().stream()
                .flatMap(worker -> toPlanningGenes(worker, planning.getAllDays()).stream());
        Chromosome planningChromosome = getChromosome();
        genes.forEach(gene -> planningChromosome.getGenes().add(gene));
    }

    private List<PlanningGene> toPlanningGenes(Worker worker, List<Integer> allDays) {
        return allDays.stream().map(day -> toPlanningGene(worker, day)).collect(Collectors.toList());
    }

    private PlanningGene toPlanningGene(Worker worker, int day) {
        return new PlanningGene(worker.getName(), worker.getDay(day));

    }

}
