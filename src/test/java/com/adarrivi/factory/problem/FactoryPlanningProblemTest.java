package com.adarrivi.factory.problem;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class FactoryPlanningProblemTest {

    private static final int PLANNING_DAYS = 14;
    private static final int ALLOWED_HOLIDAYS = 4;
    private static final String L1 = "L1";
    private static final String L2 = "L2";
    private static final String L3 = "L3";

    private FactoryPlanningProblem victim;

    @Test
    public void solveProblem() {
        givenProblem();
        whenSolveProblem();
    }

    private void givenProblem() {
        PlanningTestBuilder builder = new PlanningTestBuilder();
        builder.setAllLines(Arrays.asList(L1, L2, L3));
        builder.setPlanningDays(PLANNING_DAYS);
        builder.addWorker("A", Arrays.asList(L2), Collections.emptyList());
        builder.addWorker("B", Arrays.asList(L1), Collections.emptyList());
        builder.addWorker("C", Arrays.asList(L1), Collections.emptyList());
        builder.addWorker("D", Arrays.asList(L2, L3), Collections.emptyList());
        builder.addWorker("E", Arrays.asList(L1, L2), Collections.emptyList());
        builder.addWorker("F", Arrays.asList(L2, L3), Collections.emptyList());
        builder.addWorker("G", Arrays.asList(L2), Collections.emptyList());
        builder.addWorker("H", Arrays.asList(L1, L3), Collections.emptyList());
        builder.addWorker("I", Arrays.asList(L2), Collections.emptyList());
        builder.addWorker("J", Arrays.asList(L3), Collections.emptyList());
        builder.addWorker("K", Arrays.asList(L1), Collections.emptyList());
        PlanningProblemProperties problem = new PlanningProblemProperties();
        problem.setPrefferedPlanning(builder.build());
        problem.setAllowedHolidays(ALLOWED_HOLIDAYS);
        victim = new FactoryPlanningProblem(problem);
        victim.init();
    }

    private void whenSolveProblem() {
        victim.solve();
    }

}
