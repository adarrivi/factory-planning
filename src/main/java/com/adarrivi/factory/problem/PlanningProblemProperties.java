package com.adarrivi.factory.problem;

import com.adarrivi.factory.planning.Planning;

public class PlanningProblemProperties {

    private Planning planning;

    private double acceptableScore = 10000;

    public void setPrefferedPlanning(Planning prefferedPlanning) {
        planning = prefferedPlanning;
    }

    public Planning getPlanning() {
        return planning;
    }

    public double getAcceptableScore() {
        return acceptableScore;
    }

}
