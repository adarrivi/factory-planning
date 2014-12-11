package com.adarrivi.factory.annealing;

public class AnnealingTemperature {

    private int randomDays;

    public AnnealingTemperature(int randomDays) {
        this.randomDays = randomDays;
    }

    public void decreaseTemperature() {
        randomDays--;
    }

    public boolean isThereEnergyLeft() {
        return randomDays > 0;
    }

    public int getRandomDays() {
        return randomDays;
    }

}
