package com.adarrivi.factory.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.encog.ml.genetic.genome.CalculateGenomeScore;
import org.encog.ml.genetic.genome.Genome;

import com.adarrivi.factory.auditor.SatisfactionAuditor;

public class Planning implements CalculateGenomeScore {

    private List<String> allLines = new ArrayList<>();
    private List<Worker> allWorkers = new ArrayList<>();
    private List<Integer> allDays = new ArrayList<>();

    public int getPlanningSize() {
        return allDays.size() * allDays.size();
    }

    @Override
    public double calculateScore(Genome genome) {
        SatisfactionAuditor auditor = new SatisfactionAuditor((Planning) genome.getOrganism());
        auditor.auditPlanning();
        return auditor.getScore();
    }

    @Override
    public boolean shouldMinimize() {
        return true;
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

    public Planning duplicate() {
        Planning duplicate = new Planning();
        duplicate.allDays = allDays;
        duplicate.allLines = allLines;
        duplicate.allWorkers = allWorkers.stream().map(Worker::duplicate).collect(Collectors.toList());
        return duplicate;
    }
}
