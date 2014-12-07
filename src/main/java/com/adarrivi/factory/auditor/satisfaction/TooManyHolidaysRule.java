package com.adarrivi.factory.auditor.satisfaction;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.Worker;

class TooManyHolidaysRule extends PlanningBasedSatisfactionRule {

    private static final int INCORRECT_HOLIDAYS_PENALTY = -2000;

    TooManyHolidaysRule(Planning planning) {
        super(planning);
    }

    private void checkToManyHolidaysPerWorker(Worker worker) {
        if (worker.getHolidays().size() > planning.getMaxAllowedHolidays()) {
            score += INCORRECT_HOLIDAYS_PENALTY;
        }
    }

    @Override
    public double calculateSatisfaction() {
        planning.getAllWorkers().forEach(this::checkToManyHolidaysPerWorker);
        return score;
    }

}
