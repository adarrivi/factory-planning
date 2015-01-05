package com.adarrivi.factory.annealing;

import java.util.Collections;
import java.util.List;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

public class SensiblePlanningRandomizer {

    private Planning planning;
    private AnnealingTemperature temperature;

    public SensiblePlanningRandomizer(Planning planning, AnnealingTemperature temperature) {
        this.planning = planning;
        this.temperature = temperature;
    }

    public Planning randomizePlanning() {
        randomizeDaysAccordingToTemperature();
        randomizeHolidaysForAllWorkers();
        return planning;
    }

    private void randomizeDaysAccordingToTemperature() {
        List<Integer> randomDays = getRandomElements(temperature.getRandomDays(), planning.getAllDays());
        for (int day : randomDays) {
            clearDayForAllWorkers(day);
            randomizeDayForAllWorkers(day);
        }
    }

    private void clearDayForAllWorkers(int day) {
        planning.getAllWorkers().forEach(worker -> worker.getDay(day).setFree());
    }

    private void randomizeDayForAllWorkers(int day) {
        List<WorkerDay> requiredWorkingShifts = planning.getAllWorkingShiftsRequired(day);
        for (WorkerDay requiredDay : requiredWorkingShifts) {
            List<Worker> workersAvailable = planning.getWorkersThatCanWorkOn(requiredDay);
            List<Worker> randomWorkers = getRandomElements(1, workersAvailable);
            if (!randomWorkers.isEmpty()) {
                randomWorkers.get(0).setShift(requiredDay.getDay(), requiredDay.getLine(), requiredDay.getShiftType());
            }
        }
    }

    private void randomizeHolidaysForAllWorkers() {
        planning.getAllWorkers().forEach(this::cleanUpHolidaysForWorker);
        planning.getAllWorkers().forEach(this::randomizeHolidays);
    }

    private void cleanUpHolidaysForWorker(Worker worker) {
        worker.getHolidays().forEach(holiday -> holiday.setFree());
    }

    private void randomizeHolidays(Worker worker) {
        List<WorkerDay> holidays = getRandomElements(planning.getMandatoryHolidays(), worker.getFreeDays());
        holidays.forEach(WorkerDay::setHoliday);
    }

    private <T> List<T> getRandomElements(int maxElements, List<T> list) {
        Collections.shuffle(list);
        if (list.size() <= maxElements) {
            return list;
        }
        return list.subList(0, maxElements);
    }
}
