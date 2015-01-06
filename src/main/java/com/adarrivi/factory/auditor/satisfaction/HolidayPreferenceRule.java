package com.adarrivi.factory.auditor.satisfaction;

import java.util.List;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

class HolidayPreferenceRule extends BasicPlanningRule {

    private static final int REWARD = 4;

    HolidayPreferenceRule(Planning planning) {
        super(planning);
    }

    @Override
    public int getScorePerOccurrence() {
        return REWARD;
    }

    @Override
    public int getOccurrences() {
        planning.getAllWorkers().forEach(this::countHolidayPreferencesFulfilled);
        return occurrences;
    }

    private void countHolidayPreferencesFulfilled(Worker worker) {
        List<WorkerDay> holidayPreferences = worker.getHolidayPreferences();
        List<WorkerDay> holidays = worker.getHolidays();
        long preferencesFulfilled = holidayPreferences.stream().filter(holidays::contains).count();
        occurrences += preferencesFulfilled;
    }

}
