package com.adarrivi.factory.auditor.correctness;

import com.adarrivi.factory.planning.Planning;
import com.adarrivi.factory.planning.Worker;

class CorrectHolidaysPerWorker extends PlanningBasedCorrectnessRule {

    CorrectHolidaysPerWorker(Planning planning) {
        super(planning);
    }

    @Override
    protected void verifyCorrectness() {
        planning.getAllWorkers().forEach(this::assertWorkerHasTakenMandatoryHolidays);
    }

    private void assertWorkerHasTakenMandatoryHolidays(Worker worker) {
        if (worker.getHolidays().size() < planning.getMandatoryHolidays()) {
            errorHolder.addError("Worker " + worker.getName() + " doesn't have enough holidays");
        } else if (worker.getHolidays().size() > planning.getMandatoryHolidays()) {
            errorHolder.addError("Worker " + worker.getName() + " has too many holidays");
        }
    }

}
