package com.adarrivi.factory.auditor.rule;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.ShiftType;
import com.adarrivi.factory.planning.WorkerDay;

class UnAssignedShiftsRule extends BasicPlanningRule {

    private static final int PENALTY = -20;

    UnAssignedShiftsRule(Planning planning) {
        super(planning);
    }

    @Override
    public int getScorePerOccurrence() {
        return PENALTY;
    }

    @Override
    public int getOccurrences() {
        planning.getAllLines().forEach(line -> checkMissingShifts(line));
        return occurrences;
    }

    private void checkMissingShifts(String line) {
        planning.getAllDays().forEach(day -> checkMissingShiftForDay(line, day));
    }

    private void checkMissingShiftForDay(String line, int day) {
        List<WorkerDay> dayShifts = planning.getAllWorkingShifts(day).stream().filter(shift -> shift.getLine().equals(line))
                .collect(Collectors.toList());
        Optional<WorkerDay> morningShift = dayShifts.stream().filter(shift -> ShiftType.EARLY.equals(shift.getShiftType())).findAny();
        if (!morningShift.isPresent()) {
            occurrences++;
        }
        Optional<WorkerDay> afternoonShift = dayShifts.stream().filter(shift -> ShiftType.LATE.equals(shift.getShiftType())).findAny();
        if (!afternoonShift.isPresent()) {
            occurrences++;
        }
    }
}
