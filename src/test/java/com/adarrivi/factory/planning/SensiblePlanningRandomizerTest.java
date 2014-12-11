package com.adarrivi.factory.planning;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adarrivi.factory.annealing.AnnealingTemperature;
import com.adarrivi.factory.annealing.SensiblePlanningRandomizer;
import com.adarrivi.factory.auditor.correctness.CorrectnessAuditor;
import com.adarrivi.factory.problem.DefaultPlanningTestProblem;

public class SensiblePlanningRandomizerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensiblePlanningRandomizerTest.class);

    private SensiblePlanningRandomizer victim;
    private DefaultPlanningTestProblem problem = new DefaultPlanningTestProblem();

    @Test
    public void randomize() {
        victim = new SensiblePlanningRandomizer(problem.createDefaultProblem().getPlanning(), new AnnealingTemperature(14));
        Planning planning = victim.randomizePlanning();
        LOGGER.debug("Planning: \n{}", planning);
        CorrectnessAuditor auditor = new CorrectnessAuditor(planning);
        auditor.auditPlanning();
    }

}
