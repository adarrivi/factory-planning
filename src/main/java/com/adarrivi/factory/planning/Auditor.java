package com.adarrivi.factory.planning;

public class Auditor {

    private Planning planning;
    private double score;
    private int iteration;

    public Auditor(Planning planning, double score, int iteration) {
        this.planning = planning;
        this.score = score;
        this.iteration = iteration;

    }

    public void auditPlanning() {

    }

    public double getScore() {
        return score;
    }

}
