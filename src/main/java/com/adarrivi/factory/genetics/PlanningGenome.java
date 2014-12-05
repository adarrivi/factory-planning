package com.adarrivi.factory.genetics;

import org.encog.ml.genetic.genome.BasicGenome;
import org.encog.ml.genetic.genome.Chromosome;

import com.adarrivi.factory.planning.Planning;

public class PlanningGenome extends BasicGenome {

    private static final long serialVersionUID = 1L;

    private Chromosome planningChromosome;

    private Planning planning;

    public PlanningGenome(Planning planning) {
        this.planning = planning;
    }

    public void createRandomGenome() {
        createRandomPlanning();
        encodePlanningIntoChromosome();
        encode();
    }

    private void createRandomPlanning() {

    }

    private void encodePlanningIntoChromosome() {

    }

    @Override
    public void decode() {
        // TODO Auto-generated method stub

    }

    @Override
    public void encode() {
        // TODO Auto-generated method stub

    }

}
