package com.adarrivi.factory.annealing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adarrivi.factory.auditor.satisfaction.SatisfactionAuditor;
import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.problem.PlanningProblemProperties;

public class AnnealingPlanningSolver {

    private static Logger LOGGER = LoggerFactory.getLogger(AnnealingPlanningSolver.class);

    private PlanningProblemProperties problemProperties;
    private double currentScore = Double.MAX_VALUE;
    private double bestScore = Double.MAX_VALUE;
    private Planning bestPlanning;
    private Planning currentPlanning;
    private int iterationsAtSameTemperature = 0;
    private AnnealingTemperature temperature;

    public AnnealingPlanningSolver(PlanningProblemProperties problemProperties) {
        this.problemProperties = problemProperties;
    }

    public Planning solve() {
        temperature = problemProperties.getInitialTemperature();
        while (!finalizeCriteriaMet()) {
            calculateCurrentIterationScore();
            stepProcess();
        }
        return bestPlanning;
    }

    private boolean finalizeCriteriaMet() {
        return currentScore < problemProperties.getAcceptableScore()
                || iterationsAtSameTemperature > problemProperties.getMaxIterationsBeforeGivingUp() || !temperature.isThereEnergyLeft();
    }

    private void calculateCurrentIterationScore() {
        currentPlanning = problemProperties.getPlanning().duplicate();
        SensiblePlanningRandomizer randomizer = new SensiblePlanningRandomizer(currentPlanning, temperature);
        currentPlanning = randomizer.randomizePlanning();
        SatisfactionAuditor auditor = new SatisfactionAuditor(currentPlanning);
        auditor.auditPlanning();
        currentScore = auditor.getScore();
    }

    private void stepProcess() {
        if (bestScore > currentScore) {
            bestScore = currentScore;
            bestPlanning = currentPlanning;
            temperature.decreaseTemperature();
            iterationsAtSameTemperature = 0;
            LOGGER.debug("Better score found at temperature {}, iteration {}: {}", temperature.getRandomDays(),
                    iterationsAtSameTemperature, currentScore);
        } else {
            LOGGER.debug("Nothing new at temperature {}, iteration {}", temperature.getRandomDays(), iterationsAtSameTemperature);
            iterationsAtSameTemperature++;
        }
    }

}
