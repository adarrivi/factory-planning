package com.adarrivi.factory.auditor.satisfaction;

import java.util.List;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

class LongRestRule extends BasicPlanningRule {

    private static final int REWARD = 5;
    private static final int LONG_REST = 3;

    LongRestRule(Planning planning) {
        super(planning);
    }

    @Override
    public int getScorePerOccurrence() {
        return REWARD;
    }

    @Override
    public int getOccurrences() {
        planning.getAllWorkers().forEach(this::countConsecutiveLongHolidays);
        return occurrences;
    }

    private void countConsecutiveLongHolidays(Worker worker) {
        List<WorkerDay> holidays = worker.getHolidays();
        occurrences += countConsecutiveHolidaySeries(holidays);
    }

    private int countConsecutiveHolidaySeries(List<WorkerDay> holidays) {
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
                    if (currentSerieCount >= LONG_REST) {
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
