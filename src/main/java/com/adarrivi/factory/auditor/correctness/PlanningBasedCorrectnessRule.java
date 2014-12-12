package com.adarrivi.factory.auditor.correctness;

import com.adarrivi.factory.planning.Planning;

abstract class PlanningBasedCorrectnessRule implements CorrectnessRule {

    protected Planning planning;
    protected PlanningErrorHolder errorHolder;

    protected PlanningBasedCorrectnessRule(Planning planning) {
        this.planning = planning;
    }

    @Override
    public PlanningErrorHolder assertCorrectness(PlanningErrorHolder errorHolder) {
        this.errorHolder = errorHolder;
        verifyCorrectness();
        return this.errorHolder;
    }

    protected abstract void verifyCorrectness();

}
