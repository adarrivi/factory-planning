package com.adarrivi.factory.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.encog.ml.genetic.genome.CalculateGenomeScore;
import org.encog.ml.genetic.genome.Genome;

public class Planning implements CalculateGenomeScore {

    private List<String> allLines = new ArrayList<>();
    private List<Driver> allDrivers = new ArrayList<>();
    private List<Integer> allDays = new ArrayList<>();

    public int getPlanningSize() {
        return allDays.size() * allDays.size();
    }

    @Override
    public double calculateScore(Genome genome) {
        return 0;
    }

    @Override
    public boolean shouldMinimize() {
        return true;
    }

    public List<Driver> getAllDrivers() {
        return allDrivers;
    }

    public List<Integer> getAllDays() {
        return allDays;
    }

    public void setNewShift(String driverName, int day, int line, ShiftType shiftType) {
        Driver driver = getDriverByName(driverName);
        driver.setShift(day, line, shiftType);
    }

    private Driver getDriverByName(String driverName) {
        return allDrivers.stream().filter(driver -> driver.getName().equals(driverName)).findAny().get();
    }

    public Planning duplicate() {
        Planning duplicate = new Planning();
        duplicate.allDays = allDays;
        duplicate.allLines = allLines;
        duplicate.allDrivers = allDrivers.stream().map(Driver::duplicate).collect(Collectors.toList());
        return duplicate;
    }
}
