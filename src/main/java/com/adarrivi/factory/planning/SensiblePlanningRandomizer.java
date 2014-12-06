package com.adarrivi.factory.planning;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.adarrivi.factory.problem.PlanningProblemProperties;

public class SensiblePlanningRandomizer {

    private static final Random RANDOM = new Random();
    private static final List<ShiftType> SHIFT_TYPES = Arrays.asList(ShiftType.MORNING, ShiftType.AFTERNOON, ShiftType.HOLIDAY);
    private static final List<ShiftType> SHIFT_TYPES_NO_HOLIDAYS = Arrays.asList(ShiftType.MORNING, ShiftType.AFTERNOON);

    private PlanningProblemProperties problem;
    private Planning planning;

    public SensiblePlanningRandomizer(PlanningProblemProperties problem) {
        this.problem = problem;
        this.planning = problem.getPlanning().duplicate();
    }

    public Planning getRandomizedPlaning() {
        return planning;
    }

    private void randomizeWorker(Worker worker) {
        int holidaysLeft = problem.getAllowedHolidays();
        for (WorkerDay day : worker.getAllWorkerDays()) {
            // TODO validate global availability of the line and shift!!
            ShiftType randomShift = getRandomElement(SHIFT_TYPES);
            String randomLine = getRandomElement(worker.getAllowedLines());
            if (holidaysLeft == 0) {
                randomShift = getRandomElement(SHIFT_TYPES_NO_HOLIDAYS);
            }
            day.setShift(randomLine, randomShift);
            if (day.isHoliday()) {
                holidaysLeft--;
            }
        }
    }

    private <T> T getRandomElement(List<T> elementList) {
        return elementList.get(RANDOM.nextInt(elementList.size()));
    }

}
