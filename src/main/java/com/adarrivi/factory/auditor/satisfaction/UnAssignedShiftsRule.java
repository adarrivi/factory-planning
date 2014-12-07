package com.adarrivi.factory.auditor.satisfaction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.ShiftType;
import com.adarrivi.factory.planning.WorkerDay;

class UnAssignedShiftsRule extends PlanningBasedSatisfactionRule {
    private static final int SHIFTS_PER_DAY = 2;

    private static final int UNASSIGNED_SHIFT_PENALTY = -20;
    private static final int INCORRECT_SHIFT_PENALTY = -2000;

    UnAssignedShiftsRule(Planning planning) {
        super(planning);
    }

    private void checkMissingShifts(String line) {
        planning.getAllDays().forEach(day -> checkMissingShiftForDay(line, day));
    }

    private void checkMissingShiftForDay(String line, int day) {
        List<WorkerDay> dayShifts = planning.getAllWorkingShifts(day).stream().filter(shift -> shift.getLine().equals(line))
                .collect(Collectors.toList());
        if (dayShifts.size() > SHIFTS_PER_DAY) {
            score += INCORRECT_SHIFT_PENALTY;
            return;
        }
        Optional<WorkerDay> morningShift = dayShifts.stream().filter(shift -> ShiftType.MORNING.equals(shift.getShiftType())).findAny();
        if (!morningShift.isPresent()) {
            score += UNASSIGNED_SHIFT_PENALTY;
        }
        Optional<WorkerDay> afternoonShift = dayShifts.stream().filter(shift -> ShiftType.AFTERNOON.equals(shift.getShiftType())).findAny();
        if (!afternoonShift.isPresent()) {
            score += UNASSIGNED_SHIFT_PENALTY;
        }
    }

    @Override
    public double calculateSatisfaction() {
        planning.getAllLines().forEach(line -> checkMissingShifts(line));
        return score;
    }

}
