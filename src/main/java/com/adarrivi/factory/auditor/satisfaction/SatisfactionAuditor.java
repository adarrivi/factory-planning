package com.adarrivi.factory.auditor.satisfaction;

import java.util.ArrayList;
import java.util.List;

import com.adarrivi.factory.planning.Planning;

public class SatisfactionAuditor {

    private static final double BASIC_SCORE = 10000;

    private double score;
    private List<SatisfactionRule> satisfactionRules = new ArrayList<>();

    public SatisfactionAuditor(Planning planning) {
        setUpRules(planning);
    }

    private void setUpRules(Planning planning) {
        this.satisfactionRules.add(new WorkShiftPreferenceRule(planning));
        this.satisfactionRules.add(new HolidayPreferenceRule(planning));
        this.satisfactionRules.add(new LongRestRule(planning));
        this.satisfactionRules.add(new UnAssignedShiftsRule(planning));
        this.satisfactionRules.add(new LateFollowedByEarlyShiftRule(planning));
        this.satisfactionRules.add(new ConsecutiveLateShiftsRule(planning));
        this.satisfactionRules.add(new FairnessInLateShiftsRule(planning));
    }

    public void auditPlanning() {
        satisfactionRules.forEach(rule -> score += rule.calculateSatisfaction());
    }

    public double getScore() {
        return BASIC_SCORE - score;
    }

}
