package model;

import java.time.LocalDate;


public class WaterMeterReading {

    private final int consumerId;
    private final double previousReading;
    private final double currentReading;
    private final LocalDate readingDate;

    public WaterMeterReading(int consumerId, double previousReading, double currentReading) {
        this.consumerId = consumerId;
        this.previousReading = previousReading;
        this.currentReading = currentReading;
        this.readingDate = LocalDate.now();
    }

    public double getUnitsConsumed() {
        return currentReading - previousReading;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public double getPreviousReading() {
        return previousReading;
    }

    public double getCurrentReading() {
        return currentReading;
    }

    public LocalDate getReadingDate() {
        return readingDate;
    }
}
