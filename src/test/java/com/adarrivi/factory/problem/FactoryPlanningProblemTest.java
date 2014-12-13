package com.adarrivi.factory.problem;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adarrivi.factory.auditor.SatisfactionAuditor;
import com.adarrivi.factory.auditor.ScoreDetails;
import com.adarrivi.factory.auditor.correctness.CorrectnessAuditor;
import com.adarrivi.factory.planning.Planning;

public class FactoryPlanningProblemTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FactoryPlanningProblemTest.class);

    private FactoryPlanningProblem victim;
    private DefaultPlanningTestProblem problem = new DefaultPlanningTestProblem();

    private Planning bestPlanning;

    @Test
    public void solveProblem() {
        givenProblem();
        whenSolveProblem();
        thenVerifyCorrectness();
        thenLog();
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

    private void thenLog() {
        LOGGER.debug("Planning:\n{}", bestPlanning);
        SatisfactionAuditor satisfactionAuditor = new SatisfactionAuditor(bestPlanning);
        satisfactionAuditor.auditPlanning();
        List<ScoreDetails> allRuleScores = satisfactionAuditor.getAllRuleScores();
        for (ScoreDetails scoreDetail : allRuleScores) {
            LOGGER.debug("Score for {}: {} x {} = {}", scoreDetail.getRuleName(), scoreDetail.getOccurences(),
                    scoreDetail.getScorePerOccurence(), scoreDetail.getScore());
        }
    }

}
