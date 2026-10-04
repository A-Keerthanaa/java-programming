package util;

import java.util.regex.Pattern;


public class Validation {

    
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]{2,40}$");

    
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");

    
    private Validation() {
    }

    public static void validateName(String name) throws InvalidInputException {
        if (name == null || !NAME_PATTERN.matcher(name.trim()).matches()) {
            throw new InvalidInputException("Name must contain only letters/spaces (2-40 characters).");
        }
    }

    public static void validatePhone(String phone) throws InvalidInputException {
        if (phone == null || !PHONE_PATTERN.matcher(phone.trim()).matches()) {
            throw new InvalidInputException("Phone number must be exactly 10 digits.");
        }
    }

    public static void validateAddress(String address) throws InvalidInputException {
        if (address == null || address.trim().length() < 5) {
            throw new InvalidInputException("Address must be at least 5 characters long.");
        }
    }

    public static double validateMeterReading(String reading) throws InvalidInputException {
        double value;
        try {
            value = Double.parseDouble(reading.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Meter reading must be a valid number.");
        }
        if (value < 0) {
            throw new InvalidInputException("Meter reading cannot be negative.");
        }
        return value;
    }

    public static int validateMenuChoice(String choice, int min, int max) throws InvalidInputException {
        int value;
        try {
            value = Integer.parseInt(choice.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please enter a valid whole number.");
        }
        if (value < min || value > max) {
            throw new InvalidInputException("Choice must be between " + min + " and " + max + ".");
        }
        return value;
    }

    public static int validateConsumerId(String id) throws InvalidInputException {
        int value;
        try {
            value = Integer.parseInt(id.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Consumer ID must be a whole number.");
        }
        if (value <= 0) {
            throw new InvalidInputException("Consumer ID must be positive.");
        }
        return value;
    }
}
