package com.adarrivi.factory.auditor.correctness;

import com.adarrivi.factory.planning.Planning;

abstract class PlanningBasedCorrectnessRule implements CorrectnessRule {

    protected Planning planning;

    protected PlanningBasedCorrectnessRule(Planning planning) {
        this.planning = planning;
    }

}
