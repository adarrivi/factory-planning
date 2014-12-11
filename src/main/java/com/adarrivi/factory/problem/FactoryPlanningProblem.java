package com.adarrivi.factory.problem;

import com.adarrivi.factory.annealing.AnnealingPlanningSolver;
import com.adarrivi.factory.planning.Planning;

public class FactoryPlanningProblem {

    private AnnealingPlanningSolver solver;

    public FactoryPlanningProblem(PlanningProblemProperties problemProperties) {
        this.solver = new AnnealingPlanningSolver(problemProperties);
    }

    public Planning solve() {
        return solver.solve();
    }

}
