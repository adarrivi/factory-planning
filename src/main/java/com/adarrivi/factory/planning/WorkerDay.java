package com.adarrivi.factory.planning;

import java.util.Objects;

public class WorkerDay {

    private int day;
    private ShiftType shiftType;
    private String line;

    public WorkerDay(int day, ShiftType shiftType, String line) {
        this.day = day;
        this.shiftType = shiftType;
        this.line = line;
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

    public WorkerDay duplicate() {
        return new WorkerDay(day, shiftType, line);
    }

    void setShift(String line, ShiftType shiftType) {
        this.line = line;
        this.shiftType = shiftType;
    }

    boolean isHoliday() {
        return ShiftType.HOLIDAY.equals(shiftType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shiftType, day);
    }

    // We don't take into consideration the line because the equals/hashcode
    // should be use for compare with the preffered days, and those doesn't
    // specify the lines
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WorkerDay)) {
            return false;
        }
        WorkerDay other = (WorkerDay) obj;
        return day == other.day && shiftType.equals(other.shiftType);
    }

}
