package com.adarrivi.factory.annealing;

import com.adarrivi.factory.planning.Planning;

public class PlanningSolution {

    private Planning planning;
    private double score;
    private int planningsCreatedSoFar;
    private int temperature;

    public PlanningSolution(Planning planning, double score, int planningsCreatedSoFar, int temperature) {
        super();
        this.planning = planning;
        this.score = score;
        this.planningsCreatedSoFar = planningsCreatedSoFar;
        this.temperature = temperature;
    }

    public Planning getPlanning() {
        return planning;
    }

    public double getScore() {
        return score;
    }

    public int getPlanningsCreatedSoFar() {
        return planningsCreatedSoFar;
    }

    public int getTemperature() {
        return temperature;
    }

}
