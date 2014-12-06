package com.adarrivi.factory.auditor;

import java.util.ArrayList;
import java.util.List;

import com.adarrivi.factory.planning.Planning;

public class SatisfactionAuditor {

    private double score;
    private List<SatisfactionRule> satisfactionRules = new ArrayList<>();

    public SatisfactionAuditor(Planning planning) {
        setUpRules(planning);
    }

    private void setUpRules(Planning planning) {
        this.satisfactionRules.add(new WorkShiftPreferenceRule(planning));
        this.satisfactionRules.add(new HolidayPreferenceRule(planning));
        this.satisfactionRules.add(new LongRestRule(planning));
    }

    public void auditPlanning() {
        satisfactionRules.forEach(rule -> score += rule.calculateSatisfaction());
    }

    public double getScore() {
        return score;
    }

}
