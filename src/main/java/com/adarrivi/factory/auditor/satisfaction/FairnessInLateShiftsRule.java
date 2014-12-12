package com.adarrivi.factory.auditor.satisfaction;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.ShiftType;
import com.adarrivi.factory.planning.Worker;

public class FairnessInLateShiftsRule extends PlanningBasedSatisfactionRule {

    private static final int TOTAL_LATE_SHIFTS = 4;
    private static final int PENALTY = -8;

    FairnessInLateShiftsRule(Planning planning) {
        super(planning);
    }

    @Override
    public double calculateSatisfaction() {
        planning.getAllWorkers().forEach(this::scoreFairnessLatShifts);
        return score;
    }

    private void scoreFairnessLatShifts(Worker worker) {
        long lateShifts = worker.getWorkingDays().stream().filter(day -> ShiftType.LATE.equals(day.getShiftType())).count();
        score += Math.abs(TOTAL_LATE_SHIFTS - lateShifts) * PENALTY;
    }

}
