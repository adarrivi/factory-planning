package com.adarrivi.factory.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Worker {

    private static final Predicate<WorkerDay> HOLIDAY_ONLY_PREDICATE = (day) -> day.isHoliday();
    private static final Predicate<WorkerDay> WORKING_ONLY_PREDICATE = (day) -> !day.isHoliday();

    private String name;
    private List<String> allowedLines = new ArrayList<>();
    private List<WorkerDay> workDays = new ArrayList<>();
    private List<WorkerDay> prefferedDays = new ArrayList<>();

    public String getName() {
        return name;
    }

    List<WorkerDay> getAllWorkerDays() {
        return workDays;
    }

    List<String> getAllowedLines() {
        return allowedLines;
    }

    public Optional<WorkerDay> getDay(int day) {
        return workDays.stream().filter(workDay -> workDay.getDay() == day).findAny();
    }

    public List<WorkerDay> getWorkShiftPreferences() {
        return filterDays(prefferedDays, WORKING_ONLY_PREDICATE);
    }

    private List<WorkerDay> filterDays(List<WorkerDay> days, Predicate<WorkerDay> filterPredicate) {
        return days.stream().filter(filterPredicate).collect(Collectors.toList());
    }

    public List<WorkerDay> getHolidayPreferences() {
        return filterDays(prefferedDays, HOLIDAY_ONLY_PREDICATE);
    }

    public List<WorkerDay> getHolidays() {
        return filterDays(workDays, HOLIDAY_ONLY_PREDICATE);
    }

    public List<WorkerDay> getWorkingDays() {
        return filterDays(workDays, WORKING_ONLY_PREDICATE);
    }

    public Worker duplicate() {
        Worker duplicate = new Worker();
        duplicate.name = name;
        duplicate.allowedLines = allowedLines;
        duplicate.prefferedDays = prefferedDays;
        duplicate.workDays = workDays.stream().map(WorkerDay::duplicate).collect(Collectors.toList());
        return duplicate;
    }

    void setShift(int day, String line, ShiftType shiftType) {
        WorkerDay workingDay = workDays.stream().filter(workDay -> workDay.getDay() == day).findAny().get();
        workingDay.setShift(line, shiftType);
    }
}
