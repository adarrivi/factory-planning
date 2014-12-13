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
        randomizeWorkingDaysAccordingToTemperature();
        randomizeAllHolidays();
        return planning;
    }

    private void randomizeWorkingDaysAccordingToTemperature() {
        List<Integer> randomDays = getRandomElements(temperature.getRandomDays(), planning.getAllDays());
        for (int day : randomDays) {
            clearDayForAllWorkers(day);
            randomizeDay(day);
        }
    }

    private void clearDayForAllWorkers(int day) {
        planning.getAllWorkers().forEach(worker -> worker.getDay(day).setFree());
    }

    private void randomizeDay(int day) {
        List<WorkerDay> requiredWorkingShifts = planning.getAllWorkingShiftsRequired(day);
        for (WorkerDay requiredDay : requiredWorkingShifts) {
            List<Worker> workersAvailable = planning.getWorkersThatCanWorkOn(requiredDay);
            List<Worker> randomWorkers = getRandomElements(1, workersAvailable);
            if (!randomWorkers.isEmpty()) {
                randomWorkers.get(0).setShift(requiredDay.getDay(), requiredDay.getLine(), requiredDay.getShiftType());
            }
        }
    }

    private void randomizeAllHolidays() {
        planning.getAllWorkers().forEach(this::cleanUpHolidaysForWorker);
        planning.getAllWorkers().forEach(this::setUpHolidays);
    }

    private void cleanUpHolidaysForWorker(Worker worker) {
        worker.getHolidays().forEach(holiday -> holiday.setFree());
    }

    private void setUpHolidays(Worker worker) {
        List<WorkerDay> holidays = getRandomElements(planning.getMandatoryHolidays(), worker.getFreeDays());
        if (holidays.size() != planning.getMandatoryHolidays()) {
            throw new ImpossibleToSolveException("Cannot set the mandatory holidays for the worker " + worker.getName());
        }
        for (WorkerDay holiday : holidays) {
            holiday.setHoliday();
        }
    }

    private <T> List<T> getRandomElements(int maxElements, List<T> list) {
        Collections.shuffle(list);
        if (list.size() <= maxElements) {
            return list;
        }
        return list.subList(0, maxElements);
    }
}
