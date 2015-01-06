package com.adarrivi.factory.auditor.satisfaction;


public class ScoreDetails {

    private PlanningRule rule;
    private int occurences;

    public ScoreDetails(PlanningRule rule, int occurences) {
        super();
        this.rule = rule;
        this.occurences = occurences;
    }

    public int getOccurences() {
        return occurences;
    }

    public double getScore() {
        return occurences * rule.getScorePerOccurrence();
    }

    public int getScorePerOccurence() {
        return rule.getScorePerOccurrence();
    }

    public String getRuleName() {
        return rule.getClass().getSimpleName();
    }

}
