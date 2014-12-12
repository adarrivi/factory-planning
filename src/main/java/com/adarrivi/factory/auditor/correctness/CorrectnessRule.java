package com.adarrivi.factory.auditor.correctness;

public interface CorrectnessRule {

    PlanningErrorHolder assertCorrectness(PlanningErrorHolder errorHolder);
}
