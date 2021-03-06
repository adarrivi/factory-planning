package com.adarrivi.factory.auditor.satisfaction;

import java.util.List;
import java.util.stream.Collectors;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.ShiftType;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

class ConsecutiveLateShiftsRule extends BasicPlanningRule {

    private static final int MAX_ACCEPTABLE_CONSECUTIVE_LATE_SHIFTS = 3;
    private static final int PENALTY = -10;

    ConsecutiveLateShiftsRule(Planning planning) {
        super(planning);
    }

    @Override
    public int getScorePerOccurrence() {
        return PENALTY;
    }

    @Override
    public int getOccurrences() {
        planning.getAllWorkers().forEach(this::findConsecutiveLateShifts);
        return occurrences;
    }

    private void findConsecutiveLateShifts(Worker worker) {
        List<WorkerDay> lateShifts = worker.getWorkingDays().stream().filter(day -> ShiftType.LATE.equals(day.getShiftType()))
                .collect(Collectors.toList());
        int consecutiveLateShift = 0;
        int lastLateDay = -1;
        for (WorkerDay currentDay : lateShifts) {
            if (areConsecutive(lastLateDay, currentDay.getDay())) {
                consecutiveLateShift++;
                if (consecutiveLateShift > MAX_ACCEPTABLE_CONSECUTIVE_LATE_SHIFTS) {
                    occurrences++;
                }
            } else {
                consecutiveLateShift = 0;
            }
            lastLateDay = currentDay.getDay();
        }
    }

    private boolean areConsecutive(int yesterday, int today) {
        return (yesterday + 1) == today;
    }

}
