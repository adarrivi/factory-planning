package com.adarrivi.factory.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Planning {

    private List<String> allLines = new ArrayList<>();
    private List<Worker> allWorkers = new ArrayList<>();
    private List<Integer> allDays = new ArrayList<>();
    private int mandatoryAllowedHolidays;

    public Planning(int mandatoryHolidays, List<String> allLines, List<Worker> allWorkers, List<Integer> allDays) {
        this.mandatoryAllowedHolidays = mandatoryHolidays;
        this.allLines = allLines;
        this.allWorkers = allWorkers;
        this.allDays = allDays;
    }

    public List<String> getAllLines() {
        return allLines;
    }

    public int getMandatoryHolidays() {
        return mandatoryAllowedHolidays;
    }

    public List<Worker> getAllWorkers() {
        return allWorkers;
    }

    public List<Integer> getAllDays() {
        return allDays;
    }

    public void setNewShift(String workerName, int day, String line, ShiftType shiftType) {
        Worker worker = getWorkerByName(workerName);
        worker.setShift(day, line, shiftType);
    }

    private Worker getWorkerByName(String workerName) {
        return allWorkers.stream().filter(worker -> worker.getName().equals(workerName)).findAny().get();
    }

    public List<WorkerDay> getAllWorkingShifts(int day) {
        return getAllWorkingShiftsSameDay(day).filter(WorkerDay::isWorkingDay).collect(Collectors.toList());
    }

    private Stream<WorkerDay> getAllWorkingShiftsSameDay(int day) {
        return getAllWorkers().stream().map(aWorker -> aWorker.getDay(day));
    }

    public List<WorkerDay> getAllWorkingShiftsRequired(int day) {
        return allLines.stream().flatMap(line -> WorkerDay.createAllShiftsForDay(day, line).stream()).collect(Collectors.toList());
    }

    public List<Worker> getWorkersThatCanWorkOn(WorkerDay workerDay) {
        return allWorkers.stream().filter(worker -> worker.canWorkOn(workerDay, mandatoryAllowedHolidays)).collect(Collectors.toList());
    }

    public Planning duplicate() {
        List<Worker> duplicatedWorkers = allWorkers.stream().map(Worker::duplicate).collect(Collectors.toList());
        return new Planning(mandatoryAllowedHolidays, allLines, duplicatedWorkers, allDays);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Worker worker : allWorkers) {
            sb.append(worker).append("\n");
        }
        return sb.toString();
    }

}
