package service;

import model.Bill;
import model.Consumer;
import model.WaterMeterReading;
import util.FileManager;
import util.InvalidInputException;
import util.Validation;

import java.util.ArrayList;
import java.util.List;


public class BillingService {

    private final List<WaterMeterReading> readings = new ArrayList<>();
    private final List<Bill> bills = new ArrayList<>();

   
    public Bill generateBill(Consumer consumer, String previousReadingStr, String currentReadingStr)
            throws InvalidInputException {
        double previous = Validation.validateMeterReading(previousReadingStr);
        double current = Validation.validateMeterReading(currentReadingStr);

        if (current < previous) {
            throw new InvalidInputException("Current reading cannot be less than the previous reading.");
        }

        WaterMeterReading reading = new WaterMeterReading(consumer.getConsumerId(), previous, current);
        readings.add(reading);

        return generateBill(consumer, reading.getUnitsConsumed());
    }

    
    public Bill generateBill(Consumer consumer, double unitsConsumed) {
        double amount = consumer.calculateBill(unitsConsumed); // polymorphic call
        Bill bill = new Bill(consumer, unitsConsumed, amount);
        bills.add(bill);
        FileManager.logAction("Generated bill for consumer #" + consumer.getConsumerId()
                + " amount Rs." + String.format("%.2f", amount));
        return bill;
    }

    public List<Bill> getAllBills() {
        return bills;
    }

    public List<WaterMeterReading> getAllReadings() {
        return readings;
    }
}
