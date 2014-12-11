package com.adarrivi.factory.annealing;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

public class SensiblePlanningRandomizer {

    private static final Random RANDOM = new Random();

    private Planning planning;
    private AnnealingTemperature temperature;

    public SensiblePlanningRandomizer(Planning planning, AnnealingTemperature temperature) {
        this.planning = planning;
        this.temperature = temperature;
    }

    public Planning randomizePlanning() {
        for (int i = 0; i < temperature.getRandomDays(); i++) {
            Optional<Integer> randomDay = getRandomElement(planning.getAllDays());
            setUpRequiredWorkShifts(randomDay.get());
        }
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
