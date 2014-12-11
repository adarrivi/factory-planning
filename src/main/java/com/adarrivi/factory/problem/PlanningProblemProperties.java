package com.adarrivi.factory.problem;

import com.adarrivi.factory.annealing.AnnealingTemperature;
import com.adarrivi.factory.planning.Planning;

public class PlanningProblemProperties {

    private Planning planning;
    private AnnealingTemperature initialTemperature;
    private int maxIterationsBeforeGivingUp = 3;
    private double acceptableScore = 10000;

    public PlanningProblemProperties(AnnealingTemperature initialTemperature) {
        this.initialTemperature = initialTemperature;
    }

    public void setPrefferedPlanning(Planning prefferedPlanning) {
        planning = prefferedPlanning;
    }

    public Planning getPlanning() {
        return planning;
    }

    public AnnealingTemperature getInitialTemperature() {
        return initialTemperature;
    }

    public int getMaxIterationsBeforeGivingUp() {
        return maxIterationsBeforeGivingUp;
    }

    public double getAcceptableScore() {
        return acceptableScore;
    }

}
