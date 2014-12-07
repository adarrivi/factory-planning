package com.adarrivi.factory.auditor.correctness;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.ShiftType;
import com.adarrivi.factory.planning.WorkerDay;

class AllLinesCoveredRule extends PlanningBasedCorrectnessRule {
    private static final int SHIFTS_PER_DAY = 2;

    AllLinesCoveredRule(Planning planning) {
        super(planning);
    }

    @Override
    public void assertCorrectness() {
        planning.getAllLines().forEach(line -> assertLineIsCovered(line));
    }

    private void assertLineIsCovered(String line) {
        planning.getAllDays().forEach(day -> assertLineFullyCoveredByDay(line, day));
    }

    private void assertLineFullyCoveredByDay(String line, int day) {
        List<WorkerDay> dayShifts = planning.getAllWorkingShifts(day).stream().filter(shift -> shift.getLine().equals(line))
                .collect(Collectors.toList());

        if (dayShifts.size() > SHIFTS_PER_DAY) {
            throw new IncorrectPlanningException("Too many shifts found for line " + line + " and day " + day + ": " + dayShifts);
        }
        Optional<WorkerDay> morningShift = dayShifts.stream().filter(shift -> ShiftType.MORNING.equals(shift.getShiftType())).findAny();
        if (!morningShift.isPresent()) {
            throw new IncorrectPlanningException("No dayShift present for line " + line + " and day " + day + ": " + dayShifts);
        }
        Optional<WorkerDay> afternoonShift = dayShifts.stream().filter(shift -> ShiftType.AFTERNOON.equals(shift.getShiftType())).findAny();
        if (!afternoonShift.isPresent()) {
            throw new IncorrectPlanningException("No afternoonShift present for line " + line + " and day " + day + ": " + dayShifts);
        }
    }
}
