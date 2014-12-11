package com.adarrivi.factory.problem;

import org.junit.Test;

import com.adarrivi.factory.auditor.correctness.CorrectnessAuditor;
import com.adarrivi.factory.planning.Planning;

public class FactoryPlanningProblemTest {

    private FactoryPlanningProblem victim;
    private DefaultPlanningTestProblem problem = new DefaultPlanningTestProblem();

    private Planning bestPlanning;

    @Test
    public void solveProblem() {
        givenProblem();
        whenSolveProblem();
        thenVerifyCorrectness();
    }

    private void givenProblem() {
        victim = new FactoryPlanningProblem(problem.createDefaultProblem());
    }

    private void whenSolveProblem() {
        bestPlanning = victim.solve();
    }

    private void thenVerifyCorrectness() {
        CorrectnessAuditor auditor = new CorrectnessAuditor(bestPlanning);
        auditor.auditPlanning();
    }

}
