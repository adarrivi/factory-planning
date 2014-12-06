package com.adarrivi.factory.auditor;

import com.adarrivi.factory.planning.Planning;

abstract class PlanningBasedSatisfactionRule implements SatisfactionRule {

    protected Planning planning;
    protected double score;

    protected PlanningBasedSatisfactionRule(Planning planning) {
        this.planning = planning;
    }

}
