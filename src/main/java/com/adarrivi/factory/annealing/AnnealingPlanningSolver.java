package com.adarrivi.factory.annealing;

import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adarrivi.factory.auditor.SatisfactionAuditor;
import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.problem.PlanningProblemProperties;

public class AnnealingPlanningSolver extends Observable {

    private static Logger LOGGER = LoggerFactory.getLogger(AnnealingPlanningSolver.class);
    private static final int MAX_ITERATION_BEFORE_GIVING_UP = 50000;

    private PlanningProblemProperties problemProperties;
    private double currentScore;
    private double bestScore;
    private Planning bestPlanning;
    private Planning currentPlanning;
    private int iterationsAtSameTemperature;
    private AnnealingTemperature temperature;

    private int planningsCreated;

    public AnnealingPlanningSolver(PlanningProblemProperties problemProperties) {
        this.problemProperties = problemProperties;
    }

    public Planning solve() {
        initialize();
        // Initial planning
        bestPlanning = problemProperties.getPlanning().duplicate();
        while (!finalizeCriteriaMet()) {
            calculateCurrentIterationScore();
            stepProcess();
        }
        LOGGER.debug("Simulated annealing stoped at temperature {}, iteration {}, bestScore {}", temperature.getRandomDays(),
                iterationsAtSameTemperature, bestScore);
        LOGGER.debug("Plannings created: {}", planningsCreated);
        notifyBestSolutionFound();
        return bestPlanning;
    }

    private void initialize() {
        currentScore = Double.NEGATIVE_INFINITY;
        bestScore = Double.NEGATIVE_INFINITY;
        bestPlanning = null;
        currentPlanning = null;
        iterationsAtSameTemperature = 0;
        temperature = new AnnealingTemperature(problemProperties.getPlanning().getAllDays().size());
        planningsCreated = 0;
    }

    private void notifyBestSolutionFound() {
        setChanged();
        notifyObservers(new PlanningSolution(bestPlanning, bestScore, planningsCreated, temperature.getRandomDays()));
    }

    private boolean finalizeCriteriaMet() {
        return currentScore > problemProperties.getAcceptableScore() || iterationsAtSameTemperature > MAX_ITERATION_BEFORE_GIVING_UP
                || !temperature.isThereEnergyLeft();
    }

    private void calculateCurrentIterationScore() {
        createRandomSolution();
        planningsCreated++;
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
            notifyBestSolutionFound();
        } else {
            iterationsAtSameTemperature++;
        }
    }

}
