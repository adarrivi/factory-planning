package com.adarrivi.factory.auditor.correctness;

import java.util.ArrayList;
import java.util.List;

import com.adarrivi.factory.planning.Planning;

public class CorrectnessAuditor {

    private List<CorrectnessRule> correctnessRules = new ArrayList<>();
    private PlanningErrorHolder errorHolder = new PlanningErrorHolder();

    public CorrectnessAuditor(Planning planning) {
        setUpRules(planning);
    }

    private void setUpRules(Planning planning) {
        this.correctnessRules.add(new CorrectHolidaysPerWorker(planning));
        this.correctnessRules.add(new CorrectNumberOfShiftsPerDay(planning));
    }

    public void auditPlanning() {
        correctnessRules.forEach(rule -> errorHolder = rule.assertCorrectness(errorHolder));
        errorHolder.assertErrorFree();
    }

}
