package com.adarrivi.factory.auditor.satisfaction;

import java.util.List;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

class LongRestRule extends PlanningBasedSatisfactionRule {

    private static final int HOLIDAY_SERIES_MIN_LENGTH = 3;
    private static final int LONG_REST = 5;

    LongRestRule(Planning planning) {
        super(planning);
    }

    @Override
    public double calculateSatisfaction() {
        planning.getAllWorkers().forEach(this::calculateWorkerSatisfaction);
        return score;
    }

    private void calculateWorkerSatisfaction(Worker worker) {
        List<WorkerDay> holidays = worker.getHolidays();
        score += count3orMoreConsecutiveSeries(holidays) * LONG_REST;
    }

    private int count3orMoreConsecutiveSeries(List<WorkerDay> holidays) {
        int totalSeriesFound = 0;
        int currentSerieCount = 0;
        int lastHolidayDay = 0;
        for (WorkerDay holiday : holidays) {
            int currentHolidayDay = holiday.getDay();
            if (lastHolidayDay == 0) {
                lastHolidayDay = currentHolidayDay;
                currentSerieCount = 1;
            } else {
                if (areConsecutive(lastHolidayDay, currentHolidayDay)) {
                    currentSerieCount++;
                } else {
                    if (currentSerieCount >= HOLIDAY_SERIES_MIN_LENGTH) {
                        totalSeriesFound++;
                    }
                    currentSerieCount = 0;
                }
            }
            lastHolidayDay = currentHolidayDay;
        }
        return totalSeriesFound;
    }

    private boolean areConsecutive(int yesterday, int today) {
        return (yesterday + 1) == today;
    }
}
