package com.adarrivi.factory.auditor.correctness;

import com.adarrivi.factory.planning.Planning;

class CorrectNumberOfShiftsPerDay extends PlanningBasedCorrectnessRule {

    private static final int SHIFTS_PER_DAY = 2;

    CorrectNumberOfShiftsPerDay(Planning planning) {
        super(planning);
    }

    @Override
    protected void verifyCorrectness() {
        for (int day : planning.getAllDays()) {
            planning.getAllLines().forEach(line -> assertNumberOfShifts(day, line));
        }
    }

    private void assertNumberOfShifts(int day, String line) {
        long shifts = planning.getAllWorkingShifts(day).stream().filter(shift -> shift.getLine().equals(line)).count();
        if (shifts > SHIFTS_PER_DAY) {
            errorHolder.addError("Too many shifts for the line " + line + " and the day " + day);
        }

    }

}
