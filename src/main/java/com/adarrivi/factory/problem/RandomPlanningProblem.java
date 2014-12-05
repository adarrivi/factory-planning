package com.adarrivi.factory.problem;

public class RandomPlanningProblem {

    private PlanningProblemProperties problemProperties;
    private GeneticPlanningSolver problemSolver;

    public RandomPlanningProblem(PlanningProblemProperties problemProperties) {
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
