package com.adarrivi.factory.auditor;

import java.util.ArrayList;
import java.util.List;

import com.adarrivi.factory.auditor.rule.PlanningRule;
import com.adarrivi.factory.auditor.rule.RuleFactory;
import com.adarrivi.factory.planning.Planning;

public class SatisfactionAuditor {

    private List<PlanningRule> satisfactionRules;
    private List<ScoreDetails> allRuleScores = new ArrayList<>();

    public SatisfactionAuditor(Planning planning) {
        setUpRules(planning);
    }

    private void setUpRules(Planning planning) {
        this.satisfactionRules = RuleFactory.createSatisfactionRules(planning);
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
