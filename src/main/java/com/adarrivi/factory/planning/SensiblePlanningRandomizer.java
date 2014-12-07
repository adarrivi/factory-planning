package com.adarrivi.factory.planning;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.adarrivi.factory.auditor.correctness.CorrectnessAuditor;
import com.adarrivi.factory.problem.PlanningProblemProperties;

public class SensiblePlanningRandomizer {

    private static final Random RANDOM = new Random();

    private PlanningProblemProperties problem;
    private Planning planning;

    public SensiblePlanningRandomizer(PlanningProblemProperties problem) {
        this.problem = problem;
        this.planning = problem.getPlanning().duplicate();
    }

    public Planning getRandomizedPlaning() {
        planning.getAllWorkers().forEach(this::randomizeWorker);
        CorrectnessAuditor correctnessAuditor = new CorrectnessAuditor(planning);
        correctnessAuditor.isPlanningCorrect();
        return planning;
    }

    private void randomizeWorker(Worker worker) {
        int holidaysLeft = problem.getAllowedHolidays();
        for (WorkerDay day : worker.getAllWorkerDays()) {
            boolean includingHolidays = holidaysLeft > 0;
            Optional<WorkerDay> randomLineShift = getValidRandomLineShift(day.getDay(), worker, includingHolidays);
            if (randomLineShift.isPresent()) {
                WorkerDay shift = randomLineShift.get();
                day.setShift(shift.getLine(), shift.getShiftType());
                if (day.isHoliday()) {
                    holidaysLeft--;
                }
            }
        }
    }

    private Optional<WorkerDay> getValidRandomLineShift(int day, Worker worker, boolean includingHolidays) {
        List<WorkerDay> missingShifts = planning.getMissingShifts(day).stream()
                .filter(workerDay -> worker.getAllowedLines().contains(workerDay.getLine())).collect(Collectors.toList());
        if (includingHolidays) {
            missingShifts.add(WorkerDay.createHoliday(day));
        }
        return getRandomElement(missingShifts);
    }

    private <T> Optional<T> getRandomElement(List<T> elementList) {
        if (elementList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(elementList.get(RANDOM.nextInt(elementList.size())));
    }
}
