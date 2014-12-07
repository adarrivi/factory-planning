package com.adarrivi.factory.auditor.correctness;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.ShiftType;
import com.adarrivi.factory.planning.WorkerDay;

class AllLinesCoveredRule extends PlanningBasedCorrectnessRule {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllLinesCoveredRule.class);
    private static final int SHIFTS_PER_DAY = 2;

    AllLinesCoveredRule(Planning planning) {
        super(planning);
    }

    @Override
    public boolean isValid() {
        return planning.getAllLines().stream().filter(line -> isMissingAnyShift(line)).findAny().isPresent();
    }

    private boolean isMissingAnyShift(String line) {
        Optional<Integer> dayNotCovered = planning.getAllDays().stream().filter(day -> !isLineFullyCoveredByDay(line, day)).findAny();
        return dayNotCovered.isPresent();
    }

    private boolean isLineFullyCoveredByDay(String line, int day) {
        List<WorkerDay> dayShifts = planning.getAllWorkingShifts(day).stream().filter(shift -> shift.getLine().equals(line))
                .collect(Collectors.toList());
        if (dayShifts.size() < SHIFTS_PER_DAY) {
            LOGGER.debug("Not enough shifts found for line {} and day {}: {}", line, day, dayShifts);
            return false;
        }
        if (dayShifts.size() > SHIFTS_PER_DAY) {
            LOGGER.debug("Too many shifts found for line {} and day {}: {}", line, day, dayShifts);
            return false;
        }
        Optional<WorkerDay> morningShift = dayShifts.stream().filter(shift -> ShiftType.MORNING.equals(shift.getShiftType())).findAny();
        if (!morningShift.isPresent()) {
            LOGGER.debug("No dayShift present for line {} and day {}", line, day);
            return false;
        }
        Optional<WorkerDay> afternoonShift = dayShifts.stream().filter(shift -> ShiftType.AFTERNOON.equals(shift.getShiftType())).findAny();
        if (!afternoonShift.isPresent()) {
            LOGGER.debug("No afternoonShift present for line {} and day {}", line, day);
            return false;
        }
        return true;
    }

}
