package com.adarrivi.factory.auditor.rule;

import java.util.ArrayList;
import java.util.List;

import com.adarrivi.factory.planning.Planning;

public class RuleFactory {

    public static List<PlanningRule> createSatisfactionRules(Planning planning) {
        List<PlanningRule> satisfactionRules = new ArrayList<>();
        satisfactionRules.add(new WorkShiftPreferenceRule(planning));
        satisfactionRules.add(new HolidayPreferenceRule(planning));
        satisfactionRules.add(new LongRestRule(planning));
        satisfactionRules.add(new UnAssignedShiftsRule(planning));
        satisfactionRules.add(new LateFollowedByEarlyShiftRule(planning));
        satisfactionRules.add(new ConsecutiveLateShiftsRule(planning));
        satisfactionRules.add(new FairnessInLateShiftsRule(planning));
        return satisfactionRules;
    }

}
