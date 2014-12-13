package com.adarrivi.factory.auditor.rule;

import com.adarrivi.factory.planning.Planning;

abstract class BasicPlanningRule implements PlanningRule {

    protected Planning planning;
    protected int occurrences;

    protected BasicPlanningRule(Planning planning) {
        this.planning = planning;
    }

}
