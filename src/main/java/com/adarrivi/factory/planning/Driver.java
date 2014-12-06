package com.adarrivi.factory.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Driver {

    private String name;
    private List<String> allowedLines = new ArrayList<>();
    private List<DriverDay> workDays = new ArrayList<>();
    private List<DriverDay> prefferedDays = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Optional<DriverDay> getWorkingDay(int day) {
        return workDays.stream().filter(workDay -> workDay.getDay() == day).findAny();
    }

    public Driver duplicate() {
        Driver duplicate = new Driver();
        duplicate.name = name;
        duplicate.allowedLines = allowedLines;
        duplicate.prefferedDays = prefferedDays;
        duplicate.workDays = workDays.stream().map(DriverDay::duplicate).collect(Collectors.toList());
        return duplicate;
    }

    void setShift(int day, int line, ShiftType shiftType) {
        DriverDay workingDay = workDays.stream().filter(workDay -> workDay.getDay() == day).findAny().get();
        workingDay.setShift(line, shiftType);
    }
}
