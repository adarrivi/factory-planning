package com.adarrivi.factory.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.adarrivi.factory.planning.WorkerDay;

public class DefaultPlanningTestProblem {

    private static final int PLANNING_DAYS = 14;
    private static final String L1 = "L1";
    private static final String L2 = "L2";
    private static final String L3 = "L3";

    private static final Function<Integer, WorkerDay> HOLIDAY_SUPPLIER = (day) -> WorkerDay.createHoliday(day);
    private static final Function<Integer, WorkerDay> EARLY_SUPPLIER = (day) -> WorkerDay.createEarlyShiftPreference(day);
    private static final Function<Integer, WorkerDay> LATE_SUPPLIER = (day) -> WorkerDay.createLateShiftPreference(day);

    private PlanningTestBuilder builder;

    public PlanningProblemProperties createDefaultProblem() {
        builder = new PlanningTestBuilder();
        builder.setAllLines(Arrays.asList(L1, L2, L3));
        builder.setPlanningDays(PLANNING_DAYS);
        addWorker("A", Arrays.asList(L2), Arrays.asList(8, 14), Arrays.asList(9, 12, 13), Arrays.asList(7));
        addWorker("B", Arrays.asList(L1), Arrays.asList(7, 8), Arrays.asList(6), Arrays.asList(1, 9));
        addWorker("C", Arrays.asList(L1), Arrays.asList(1, 10), Arrays.asList(8), Arrays.asList(14));
        addWorker("D", Arrays.asList(L2, L3), Arrays.asList(3, 4, 11), Arrays.asList(10), Collections.emptyList());
        addWorker("E", Arrays.asList(L1, L2), Arrays.asList(10, 11), Arrays.asList(12, 13), Collections.emptyList());
        addWorker("F", Arrays.asList(L2, L3), Arrays.asList(6, 12), Arrays.asList(5), Arrays.asList(3, 14));
        addWorker("G", Arrays.asList(L2), Arrays.asList(5, 6, 14), Arrays.asList(1, 7), Collections.emptyList());
        addWorker("H", Arrays.asList(L1, L3), Arrays.asList(1, 6, 7), Arrays.asList(2, 9), Collections.emptyList());
        addWorker("I", Arrays.asList(L2), Arrays.asList(14), Collections.emptyList(), Arrays.asList(6, 8));
        addWorker("J", Arrays.asList(L3), Arrays.asList(1, 8, 9), Arrays.asList(11), Arrays.asList(2, 3));
        addWorker("K", Arrays.asList(L1), Arrays.asList(3, 9), Arrays.asList(8), Arrays.asList(5));
        PlanningProblemProperties problem = new PlanningProblemProperties();
        problem.setPrefferedPlanning(builder.build());
        return problem;
    }

    private void addWorker(String workerName, List<String> allowedLines, List<Integer> preferredHolidays,
            List<Integer> preferredEarlyShifts, List<Integer> preferredLateShifts) {
        List<WorkerDay> preferences = createPreferenceList(HOLIDAY_SUPPLIER, preferredHolidays);
        preferences.addAll(createPreferenceList(EARLY_SUPPLIER, preferredEarlyShifts));
        preferences.addAll(createPreferenceList(LATE_SUPPLIER, preferredLateShifts));
        builder.addWorker(workerName, allowedLines, preferences);
    }

    private List<WorkerDay> createPreferenceList(Function<Integer, WorkerDay> daySupplier, List<Integer> days) {
        List<WorkerDay> preferences = new ArrayList<>();
        for (int day : days) {
            preferences.add(daySupplier.apply(day));
        }
        return preferences;
    }

}
