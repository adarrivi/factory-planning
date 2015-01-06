package com.adarrivi.factory.annealing;

public class AnnealingTemperature {

    private static final int TEMPERATURE_DAYS_COEFICIENT = 2;

    private int temperature;

    public AnnealingTemperature(int randomDays) {
        this.temperature = randomDays * TEMPERATURE_DAYS_COEFICIENT;
    }

    public void decreaseTemperature() {
        temperature--;
    }

    public boolean isThereEnergyLeft() {
        return temperature > 0;
    }

    public int getRandomDays() {
        return Math.floorDiv(temperature, TEMPERATURE_DAYS_COEFICIENT);
    }

}
