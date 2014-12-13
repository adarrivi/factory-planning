package com.adarrivi.factory.auditor.rule;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.ShiftType;
import com.adarrivi.factory.planning.Worker;

class FairnessInLateShiftsRule extends BasicPlanningRule {

    private static final int TOTAL_LATE_SHIFTS = 4;
    private static final int PENALTY = -8;

    FairnessInLateShiftsRule(Planning planning) {
        super(planning);
    }

    @Override
    public int getScorePerOccurrence() {
        return PENALTY;
    }

    @Override
    public int getOccurrences() {
        planning.getAllWorkers().forEach(this::countFairnessLatShifts);
        return occurrences;
    }

    private void countFairnessLatShifts(Worker worker) {
        long lateShifts = worker.getWorkingDays().stream().filter(day -> ShiftType.LATE.equals(day.getShiftType())).count();
        occurrences += Math.abs(TOTAL_LATE_SHIFTS - lateShifts);
    }

}
