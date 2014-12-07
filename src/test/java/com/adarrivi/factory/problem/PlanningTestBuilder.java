package com.adarrivi.factory.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.Worker;
import com.adarrivi.factory.planning.WorkerDay;

public class PlanningTestBuilder {

    private List<Integer> allDays;
    private List<String> allLines;
    private List<Worker> allWorkers = new ArrayList<>();

    public void addWorker(String workerName, List<String> allowedLines, List<WorkerDay> preferences) {
        allWorkers.add(Worker.createWorkerEmptyPlanning(workerName, allowedLines, allDays, preferences));
    }

    public void setPlanningDays(int planningDays) {
        allDays = IntStream.range(1, planningDays + 1).boxed().collect(Collectors.toList());
    }

    public void setAllLines(List<String> allLines) {
        this.allLines = allLines;
    }

    public Planning build() {
        return new Planning(allLines, allWorkers, allDays);
    }

}
