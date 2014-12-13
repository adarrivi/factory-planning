package com.adarrivi.factory.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkerDay {

    private static final String EMPTY_STRING = "";

    private int day;
    private ShiftType shiftType;
    private String line;

    private WorkerDay(int day, ShiftType shiftType, String line) {
        this.day = day;
        this.shiftType = shiftType;
        this.line = line;
    }

    public static WorkerDay createEmptyDay(int day) {
        return new WorkerDay(day, ShiftType.FREE, EMPTY_STRING);
    }

    public static WorkerDay createHoliday(int day) {
        return new WorkerDay(day, ShiftType.HOLIDAY, EMPTY_STRING);
    }

    public static WorkerDay createEarlyShiftPreference(int day) {
        return new WorkerDay(day, ShiftType.EARLY, EMPTY_STRING);
    }

    public static WorkerDay createLateShiftPreference(int day) {
        return new WorkerDay(day, ShiftType.LATE, EMPTY_STRING);
    }

    public static List<WorkerDay> createAllShiftsForDay(int day, String line) {
        List<WorkerDay> shifts = new ArrayList<>();
        shifts.add(new WorkerDay(day, ShiftType.EARLY, line));
        shifts.add(new WorkerDay(day, ShiftType.LATE, line));
        return shifts;
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

    public void setHoliday() {
        this.shiftType = ShiftType.HOLIDAY;
        this.line = EMPTY_STRING;
    }

    boolean isHoliday() {
        return ShiftType.HOLIDAY.equals(shiftType);
    }

    boolean isWorkingDay() {
        return ShiftType.EARLY.equals(shiftType) || ShiftType.LATE.equals(shiftType);
    }

    boolean isFree() {
        return ShiftType.FREE.equals(shiftType);
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

    @Override
    public String toString() {
        return "[day=" + day + ", line=" + line + ", shift: " + shiftType + "]";
    }

}
