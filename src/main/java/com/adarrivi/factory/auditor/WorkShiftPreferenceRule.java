package com.adarrivi.factory.auditor;

import java.util.List;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

class WorkShiftPreferenceRule extends PlanningBasedSatisfactionRule {

    private static final int RESPECT_SINGLE_SHIFT_PREFERENCE = 3;

    WorkShiftPreferenceRule(Planning planning) {
        super(planning);
    }

    @Override
    public double calculateSatisfaction() {
        planning.getAllWorkers().forEach(this::calculateWorkerSatisfaction);
        return score;
    }

    private void calculateWorkerSatisfaction(Worker worker) {
        List<WorkerDay> workShiftPreferences = worker.getWorkShiftPreferences();
        List<WorkerDay> workingDays = worker.getWorkingDays();
        long preferencesFulfilled = workShiftPreferences.stream().filter(workingDays::contains).count();
        score += preferencesFulfilled * RESPECT_SINGLE_SHIFT_PREFERENCE;
    }

}
