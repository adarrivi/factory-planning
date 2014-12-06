package com.adarrivi.factory.planning;

public class DriverDay {

    private int day;
    private ShiftType shiftType;
    private int line;

    public DriverDay(int day, ShiftType shiftType, int line) {
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

    public int getLine() {
        return line;
    }

    public DriverDay duplicate() {
        return new DriverDay(day, shiftType, line);
    }

    void setShift(int line, ShiftType shiftType) {
        this.line = line;
        this.shiftType = shiftType;
    }

}
