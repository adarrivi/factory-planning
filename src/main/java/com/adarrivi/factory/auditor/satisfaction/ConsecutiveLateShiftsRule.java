package com.adarrivi.factory.auditor.satisfaction;

import java.util.List;
import java.util.stream.Collectors;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.ShiftType;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

public class ConsecutiveLateShiftsRule extends PlanningBasedSatisfactionRule {

    private static final int MAX_ACCEPTABLE_CONSECUTIVE_LATE_SHIFTS = 3;
    private static final int PENALTY = -10;

    ConsecutiveLateShiftsRule(Planning planning) {
        super(planning);
    }

    @Override
    public double calculateSatisfaction() {
        planning.getAllWorkers().forEach(this::scoreConsecutiveLateShifts);
        return score;
    }

    private void scoreConsecutiveLateShifts(Worker worker) {
        List<WorkerDay> lateShifts = worker.getWorkingDays().stream().filter(day -> ShiftType.LATE.equals(day.getShiftType()))
                .collect(Collectors.toList());
        int consecutiveLateShift = 0;
        int lastLateDay = -1;
        for (WorkerDay currentDay : lateShifts) {
            if (areConsecutive(lastLateDay, currentDay.getDay())) {
                consecutiveLateShift++;
                if (consecutiveLateShift > MAX_ACCEPTABLE_CONSECUTIVE_LATE_SHIFTS) {
                    score += PENALTY;
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
