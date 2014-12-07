package com.adarrivi.factory.auditor.satisfaction;

import java.util.List;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

class HolidayPreferenceRule extends PlanningBasedSatisfactionRule {

    private static final int RESPECT_HOLIDAY_PREFERENCE = 4;

    HolidayPreferenceRule(Planning planning) {
        super(planning);
    }

    @Override
    public double calculateSatisfaction() {
        planning.getAllWorkers().forEach(this::calculateWorkerSatisfaction);
        return score;
    }

    private void calculateWorkerSatisfaction(Worker worker) {
        List<WorkerDay> holidayPreferences = worker.getHolidayPreferences();
        List<WorkerDay> holidays = worker.getHolidays();
        long preferencesFulfilled = holidayPreferences.stream().filter(holidays::contains).count();
        score += preferencesFulfilled * RESPECT_HOLIDAY_PREFERENCE;
    }
}
