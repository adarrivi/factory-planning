package com.adarrivi.factory.problem;

import com.adarrivi.factory.planning.Planning;

public class PlanningProblemProperties {

    private static final int DEFAULT_POPULATION_SIZE = 1000;
    private static final double DEFAULT_MUTATION_PERCENT = 0.1;
    private static final double DEFAULT_PERCENT_TO_MATE = 0.24;
    private static final double DEFAULT_MATING_POPULATION_PERCENT = 0.5;
    private static final int DEFAULT_MAX_SAME_SOLUTION = 50;
    private static final int DEFAULT_CROSSOVER_SLICES = 3;

    private int allowedHolidays;
    private Planning planning;

    public void setPrefferedPlanning(Planning prefferedPlanning) {
        planning = prefferedPlanning;
    }

    public void setAllowedHolidays(int allowedHolidays) {
        this.allowedHolidays = allowedHolidays;
    }

    public double getMutationPercent() {
        return DEFAULT_MUTATION_PERCENT;
    }

    public double getPercentToMate() {
        return DEFAULT_PERCENT_TO_MATE;
    }

    public double getMatingPopulationPercent() {
        return DEFAULT_MATING_POPULATION_PERCENT;
    }

    public int getCrossoverSlices() {
        return DEFAULT_CROSSOVER_SLICES;
    }

    public int getMaxSameSolution() {
        return DEFAULT_MAX_SAME_SOLUTION;
    }

    public int getPopulationSize() {
        return DEFAULT_POPULATION_SIZE;
    }

    public Planning getPlanning() {
        return planning;
    }

    public int getAllowedHolidays() {
        return allowedHolidays;
    }

}
