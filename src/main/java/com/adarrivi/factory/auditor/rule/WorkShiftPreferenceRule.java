package com.adarrivi.factory.auditor.rule;

import java.util.List;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

class WorkShiftPreferenceRule extends BasicPlanningRule {

    private static final int REWARD = 3;

    WorkShiftPreferenceRule(Planning planning) {
        super(planning);
    }

    @Override
    public int getScorePerOccurrence() {
        return REWARD;
    }

    @Override
    public int getOccurrences() {
        planning.getAllWorkers().forEach(this::calculateWorkerSatisfaction);
        return occurrences;
    }

    private void calculateWorkerSatisfaction(Worker worker) {
        List<WorkerDay> workShiftPreferences = worker.getWorkShiftPreferences();
        List<WorkerDay> workingDays = worker.getWorkingDays();
        long preferencesFulfilled = workShiftPreferences.stream().filter(workingDays::contains).count();
        occurrences += preferencesFulfilled;
    }

}
