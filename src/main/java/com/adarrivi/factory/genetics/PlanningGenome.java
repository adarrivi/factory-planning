package com.adarrivi.factory.genetics;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.encog.ml.genetic.genome.BasicGenome;
import org.encog.ml.genetic.genome.Chromosome;

import com.adarrivi.factory.planning.Driver;
import com.adarrivi.factory.planning.DriverDay;
import com.adarrivi.factory.planning.Planning;

public class PlanningGenome extends BasicGenome {

    private static final long serialVersionUID = 1L;

    public PlanningGenome() {
        getChromosomes().add(new Chromosome());
    }

    // This method should be called only once
    public void createRandomGenome(Planning preferencePlanning) {
        createRandomPlanning(preferencePlanning);
        encode();
    }

    private Chromosome getChromosome() {
        return getChromosomes().get(0);
    }

    private void createRandomPlanning(Planning preferencePlanning) {
        Planning planning = preferencePlanning.duplicate();
        // TODO create random
        setOrganism(planning);
    }

    private List<PlanningGene> toPlanningGenes(Driver driver, List<Integer> allDays) {
        return allDays.stream().map(day -> toPlanningGene(driver, day)).collect(Collectors.toList());
    }

    private PlanningGene toPlanningGene(Driver driver, int day) {
        DriverDay workingDay = driver.getWorkingDay(day).get();
        return new PlanningGene(driver.getName(), workingDay);

    }

    @Override
    public void decode() {
        Planning planning = (Planning) getOrganism();
        Chromosome planningChromosome = getChromosome();
        for (int i = 0; i < planningChromosome.size(); i++) {
            PlanningGene gene = (PlanningGene) planningChromosome.get(i);
            planning.setNewShift(gene.getDriverName(), gene.getDay(), gene.getLine(), gene.getShiftType());
        }
        setOrganism(planning);
    }

    @Override
    public void encode() {
        Planning planning = (Planning) getOrganism();
        Stream<PlanningGene> genes = planning.getAllDrivers().stream()
                .flatMap(driver -> toPlanningGenes(driver, planning.getAllDays()).stream());
        Chromosome planningChromosome = getChromosome();
        genes.forEach(gene -> planningChromosome.getGenes().add(gene));
    }

}
