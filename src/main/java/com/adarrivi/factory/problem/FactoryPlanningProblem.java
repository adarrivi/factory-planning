package com.adarrivi.factory.problem;

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

    public void solve() {
        problemSolver.solve();
    }

}
