package com.adarrivi.factory.auditor.satisfaction;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.ShiftType;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

class LateFollowedByEarlyShiftRule extends BasicPlanningRule {

    private static final int PENALTY = -30;

    LateFollowedByEarlyShiftRule(Planning planning) {
        super(planning);
    }

    @Override
    public int getScorePerOccurrence() {
        return PENALTY;
    }

    @Override
    public int getOccurrences() {
        planning.getAllWorkers().forEach(this::countLateEarlyShifts);
        return occurrences;
    }

    private void countLateEarlyShifts(Worker worker) {
        ShiftType previousShift = ShiftType.FREE;
        for (WorkerDay day : worker.getWorkingDays()) {
            ShiftType currentShift = day.getShiftType();
            if (ShiftType.LATE.equals(previousShift) && ShiftType.EARLY.equals(currentShift)) {
                occurrences++;
            }
            previousShift = currentShift;
        }
    }
}
