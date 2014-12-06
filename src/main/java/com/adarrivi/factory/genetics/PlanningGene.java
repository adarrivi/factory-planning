package com.adarrivi.factory.genetics;

import java.util.Objects;

import org.encog.ml.genetic.genes.BasicGene;
import org.encog.ml.genetic.genes.Gene;

import com.adarrivi.factory.planning.DriverDay;
import com.adarrivi.factory.planning.ShiftType;

public class PlanningGene extends BasicGene {

    private static final long serialVersionUID = 1L;
    private String driverName;
    private int day;
    private ShiftType shiftType;
    private int line;

    public PlanningGene(String driverName, DriverDay workDay) {
        this.driverName = driverName;
        this.day = workDay.getDay();
        this.shiftType = workDay.getShiftType();
        this.line = workDay.getLine();
    }

    @Override
    public void copy(Gene fromGene) {
        PlanningGene fromPlannignGene = (PlanningGene) fromGene;
        this.driverName = fromPlannignGene.driverName;
        this.day = fromPlannignGene.day;
        this.shiftType = fromPlannignGene.shiftType;
        this.line = fromPlannignGene.line;
    }

    public String getDriverName() {
        return driverName;
    }

    public int getDay() {
        return day;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public int getLine() {
        return line;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(driverName, day, shiftType, line);
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
        return driverName.equals(other.driverName) && day == other.day && shiftType.equals(other.shiftType) && line == other.line;
    }

}
