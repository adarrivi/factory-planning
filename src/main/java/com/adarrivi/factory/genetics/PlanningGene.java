package com.adarrivi.factory.genetics;

import java.util.Objects;

import org.encog.ml.genetic.genes.BasicGene;
import org.encog.ml.genetic.genes.Gene;

import com.adarrivi.factory.planning.ShiftType;
import com.adarrivi.factory.planning.WorkerDay;

public class PlanningGene extends BasicGene {

    private static final long serialVersionUID = 1L;
    private String workerName;
    private int day;
    private ShiftType shiftType;
    private String line;

    public PlanningGene(String workerName, WorkerDay workDay) {
        this.workerName = workerName;
        this.day = workDay.getDay();
        this.shiftType = workDay.getShiftType();
        this.line = workDay.getLine();
    }

    @Override
    public void copy(Gene fromGene) {
        PlanningGene fromPlannignGene = (PlanningGene) fromGene;
        this.workerName = fromPlannignGene.workerName;
        this.day = fromPlannignGene.day;
        this.shiftType = fromPlannignGene.shiftType;
        this.line = fromPlannignGene.line;
    }

    public String getWorkerName() {
        return workerName;
    }

    public int getDay() {
        return day;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public String getLine() {
        return line;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(workerName, day, shiftType, line);
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PlanningGene)) {
            return false;
        }
        PlanningGene other = (PlanningGene) obj;
        // neither of the attributes should have a null value
        return workerName.equals(other.workerName) && day == other.day && shiftType.equals(other.shiftType) && line == other.line;
    }

}
