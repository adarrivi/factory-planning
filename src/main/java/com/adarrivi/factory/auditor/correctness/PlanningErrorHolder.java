package com.adarrivi.factory.auditor.correctness;

import java.util.ArrayList;
import java.util.List;

class PlanningErrorHolder {

    private List<String> errors = new ArrayList<>();

    void addError(String errorDescription) {
        errors.add(errorDescription);
    }

    void assertErrorFree() {
        if (!errors.isEmpty()) {
            throw new IncorrectPlanningException("Incorrect planning:\n Error: " + String.join("\n Error: ", errors));
        }
    }
}
