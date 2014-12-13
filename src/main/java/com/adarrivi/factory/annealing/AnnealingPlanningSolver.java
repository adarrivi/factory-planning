package com.adarrivi.factory.annealing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adarrivi.factory.auditor.SatisfactionAuditor;
import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.problem.PlanningProblemProperties;

public class AnnealingPlanningSolver {

    private static Logger LOGGER = LoggerFactory.getLogger(AnnealingPlanningSolver.class);
    private static final int MAX_ITERATION_BEFORE_GIVING_UP = 3000;

    private PlanningProblemProperties problemProperties;
    private double currentScore = Double.NEGATIVE_INFINITY;
    private double bestScore = Double.NEGATIVE_INFINITY;
    private Planning bestPlanning;
    private Planning currentPlanning;
    private int iterationsAtSameTemperature = 0;
    private AnnealingTemperature temperature;

    private int goodSolutionsCreated = 0;
    private int badSolutionsCreated = 0;

    public AnnealingPlanningSolver(PlanningProblemProperties problemProperties) {
        this.problemProperties = problemProperties;
        this.temperature = new AnnealingTemperature(problemProperties.getPlanning().getAllDays().size());
    }

    public Planning solve() {
        // Initial planning
        bestPlanning = problemProperties.getPlanning().duplicate();
        while (!finalizeCriteriaMet()) {
            calculateCurrentIterationScore();
            stepProcess();
        }
        LOGGER.debug("Simulated annealing stoped at temperature {}, iteration {}, bestScore {}", temperature.getRandomDays(),
                iterationsAtSameTemperature, bestScore);
        LOGGER.debug("Good solutions: {}, bad ones {}", goodSolutionsCreated, badSolutionsCreated);
        return bestPlanning;
    }

    private boolean finalizeCriteriaMet() {
        return currentScore > problemProperties.getAcceptableScore() || iterationsAtSameTemperature > MAX_ITERATION_BEFORE_GIVING_UP
                || !temperature.isThereEnergyLeft();
    }

    private void calculateCurrentIterationScore() {
        boolean randomSolutionCreated = false;
        while (!randomSolutionCreated) {
            try {
                createRandomSolution();
                goodSolutionsCreated++;
                randomSolutionCreated = true;
            } catch (ImpossibleToSolveException ex) {
                badSolutionsCreated++;
                randomSolutionCreated = false;

            }
        }
    }

    private void createRandomSolution() {
        currentPlanning = bestPlanning.duplicate();
        SensiblePlanningRandomizer randomizer = new SensiblePlanningRandomizer(currentPlanning, temperature);
        currentPlanning = randomizer.randomizePlanning();
        SatisfactionAuditor auditor = new SatisfactionAuditor(currentPlanning);
        auditor.auditPlanning();
        currentScore = auditor.getTotalScore();
    }

    private void stepProcess() {
        if (bestScore < currentScore) {
            bestScore = currentScore;
            bestPlanning = currentPlanning;
            temperature.decreaseTemperature();
            iterationsAtSameTemperature = 0;
            LOGGER.debug("Better score found at temperature {}, iteration {}: {}", temperature.getRandomDays(),
                    iterationsAtSameTemperature, currentScore);
        } else {
            iterationsAtSameTemperature++;
        }
    }

}
