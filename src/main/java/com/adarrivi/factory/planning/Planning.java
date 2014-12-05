package com.adarrivi.factory.planning;

import java.util.ArrayList;
import java.util.List;

import org.encog.ml.genetic.genome.CalculateGenomeScore;
import org.encog.ml.genetic.genome.Genome;

public class Planning implements CalculateGenomeScore {

    private List<String> allLines = new ArrayList<>();
    private List<Driver> allDrivers = new ArrayList<>();
    private List<Integer> allDays = new ArrayList<>();

    public int getPlanningSize() {
        return allDays.size() * allDays.size();
    }

    @Override
    public double calculateScore(Genome genome) {
        return 0;
    }

    @Override
    public boolean shouldMinimize() {
        return true;
    }

}
