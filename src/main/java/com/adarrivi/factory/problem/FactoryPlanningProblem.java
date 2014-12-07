package com.adarrivi.factory.problem;

import com.adarrivi.factory.planning.Planning;

public class FactoryPlanningProblem {

    private PlanningProblemProperties problemProperties;
    private GeneticPlanningSolver problemSolver;

    public FactoryPlanningProblem(PlanningProblemProperties problemProperties) {
        this.problemProperties = problemProperties;
    }

    public void init() {
        problemSolver = new GeneticPlanningSolver(problemProperties);
        problemSolver.init();
    }

    public Planning solve() {
        return problemSolver.solve();

    }

}
