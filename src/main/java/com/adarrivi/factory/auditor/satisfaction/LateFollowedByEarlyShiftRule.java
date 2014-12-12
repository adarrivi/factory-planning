package com.adarrivi.factory.auditor.satisfaction;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.ShiftType;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

class LateFollowedByEarlyShiftRule extends PlanningBasedSatisfactionRule {

    private static final int PENALTY = -30;

    LateFollowedByEarlyShiftRule(Planning planning) {
        super(planning);
    }

    @Override
    public double calculateSatisfaction() {
        planning.getAllWorkers().forEach(this::checkLateEarlyShifts);
        return score;
    }

    private void checkLateEarlyShifts(Worker worker) {
        ShiftType previousShift = ShiftType.FREE;
        for (WorkerDay day : worker.getWorkingDays()) {
            ShiftType currentShift = day.getShiftType();
            if (ShiftType.LATE.equals(previousShift) && ShiftType.EARLY.equals(currentShift)) {
                score += PENALTY;
            }
            previousShift = currentShift;
        }
    }
}
