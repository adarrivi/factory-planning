package com.adarrivi.factory.planning;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.adarrivi.factory.problem.PlanningProblemProperties;

public class SensiblePlanningRandomizer {

    private static final Random RANDOM = new Random();

    private Planning planning;

    public SensiblePlanningRandomizer(PlanningProblemProperties problem) {
        this.planning = problem.getPlanning().duplicate();
    }

    public Planning getRandomizedPlaning() {
        planning.getAllDays().forEach(this::setUpRequiredWorkShifts);
        planning.getAllWorkers().forEach(this::setUpHolidays);
        return planning;
    }

    private void setUpRequiredWorkShifts(int day) {
        List<WorkerDay> requiredWorkingShifts = planning.getAllWorkingShiftsRequired(day);
        for (WorkerDay requiredDay : requiredWorkingShifts) {
            List<Worker> workersAvailable = planning.getWorkersThatCanWorkOn(requiredDay);
            Optional<Worker> randomWorker = getRandomElement(workersAvailable);
            if (randomWorker.isPresent()) {
                randomWorker.get().setShift(requiredDay.getDay(), requiredDay.getLine(), requiredDay.getShiftType());
            }
        }
    }

    private void setUpHolidays(Worker worker) {
        List<WorkerDay> freeDays = worker.getFreeDays();
        for (int i = 0; i < planning.getMaxAllowedHolidays(); i++) {
            Optional<WorkerDay> randomElement = getRandomElement(freeDays);
            if (randomElement.isPresent()) {
                WorkerDay newHoliday = randomElement.get();
                newHoliday.setHoliday();
                freeDays.remove(newHoliday);
            }
        }
    }

    private <T> Optional<T> getRandomElement(List<T> elementList) {
        if (elementList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(elementList.get(RANDOM.nextInt(elementList.size())));
    }
}
