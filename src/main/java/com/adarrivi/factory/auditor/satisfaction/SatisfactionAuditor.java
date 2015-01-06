package com.adarrivi.factory.auditor.satisfaction;

import java.util.ArrayList;
import java.util.List;

import com.adarrivi.factory.planning.Planning;

public class SatisfactionAuditor {

    private List<PlanningRule> satisfactionRules;
    private List<ScoreDetails> allRuleScores = new ArrayList<>();

    public SatisfactionAuditor(Planning planning) {
        setUpRules(planning);
    }

    private void setUpRules(Planning planning) {
        satisfactionRules = new ArrayList<>();
        satisfactionRules.add(new WorkShiftPreferenceRule(planning));
        satisfactionRules.add(new HolidayPreferenceRule(planning));
        satisfactionRules.add(new LongRestRule(planning));
        satisfactionRules.add(new UnAssignedShiftsRule(planning));
        satisfactionRules.add(new LateFollowedByEarlyShiftRule(planning));
        satisfactionRules.add(new ConsecutiveLateShiftsRule(planning));
        satisfactionRules.add(new FairnessInLateShiftsRule(planning));
    }

    public void auditPlanning() {
        satisfactionRules.forEach(this::calculateScore);
    }

    private void calculateScore(PlanningRule rule) {
        ScoreDetails scoreDetails = new ScoreDetails(rule, rule.getOccurrences());
        allRuleScores.add(scoreDetails);
    }

    public double getTotalScore() {
        double aggregatedScore = 0;
        for (ScoreDetails score : allRuleScores) {
            aggregatedScore += score.getScore();
        }
        return aggregatedScore;
    }

    public List<ScoreDetails> getAllRuleScores() {
        return allRuleScores;
    }

}
