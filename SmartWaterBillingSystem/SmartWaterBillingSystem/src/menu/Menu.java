package menu;

import model.Bill;
import model.Consumer;
import service.BillingService;
import service.ConsumerService;
import util.ConsumerNotFoundException;
import util.FileManager;
import util.InvalidInputException;
import util.Validation;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsumerService consumerService;
    private final BillingService billingService;

    public Menu(ConsumerService consumerService, BillingService billingService) {
        this.consumerService = consumerService;
        this.billingService = billingService;
    }

    public void run() {
        boolean running = true;
        System.out.println("==========================================");
        System.out.println(" SMART WATER BILLING SYSTEM - CONSOLE APP");
        System.out.println("==========================================");

        while (running) {
            printMenu();
            try {
                int choice = Validation.validateMenuChoice(scanner.nextLine(), 0, 7);
                switch (choice) {
                    case 1 -> addConsumer();
                    case 2 -> searchConsumer();
                    case 3 -> updateConsumer();
                    case 4 -> deleteConsumer();
                    case 5 -> listConsumers();
                    case 6 -> generateBill();
                    case 7 -> backupNow();
                    case 0 -> {
                        running = false;
                        System.out.println("Goodbye!");
                    }
                    default -> System.out.println("Unhandled option.");
                }
            } catch (InvalidInputException e) {
                // Error handling: never crash on bad input, just report and re-loop
                System.out.println("Input error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n1. Add Consumer");
        System.out.println("2. Search Consumer");
        System.out.println("3. Update Consumer");
        System.out.println("4. Delete Consumer");
        System.out.println("5. List All Consumers");
        System.out.println("6. Generate Bill");
        System.out.println("7. Backup Data Now");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void addConsumer() throws InvalidInputException {
        System.out.print("Consumer type (1-Residential / 2-Commercial): ");
        int type = Validation.validateMenuChoice(scanner.nextLine(), 1, 2);

        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone (10 digits): ");
        String phone = scanner.nextLine();

        Consumer consumer = (type == 1)
                ? consumerService.addResidentialConsumer(name, address, phone)
                : consumerService.addCommercialConsumer(name, address, phone);

        System.out.println("Consumer added successfully with ID: " + consumer.getConsumerId());
    }

    private void searchConsumer() {
        System.out.print("Search by (1-ID / 2-Name): ");
        try {
            int mode = Validation.validateMenuChoice(scanner.nextLine(), 1, 2);
            if (mode == 1) {
                System.out.print("Enter Consumer ID: ");
                int id = Validation.validateConsumerId(scanner.nextLine());
                Consumer c = consumerService.searchById(id);
                System.out.println(c);
            } else {
                System.out.print("Enter name (or part of it): ");
                String name = scanner.nextLine();
                List<Consumer> results = consumerService.searchByName(name);
                if (results.isEmpty()) {
                    System.out.println("No matches found.");
                } else {
                    results.forEach(System.out::println);
                }
            }
        } catch (InvalidInputException | ConsumerNotFoundException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
    }

    private void updateConsumer() {
        try {
            System.out.print("Enter Consumer ID to update: ");
            int id = Validation.validateConsumerId(scanner.nextLine());
            System.out.print("New Address: ");
            String address = scanner.nextLine();
            System.out.print("New Phone: ");
            String phone = scanner.nextLine();
            consumerService.updateConsumer(id, address, phone);
            System.out.println("Consumer #" + id + " updated successfully.");
        } catch (InvalidInputException | ConsumerNotFoundException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    private void deleteConsumer() {
        try {
            System.out.print("Enter Consumer ID to delete: ");
            int id = Validation.validateConsumerId(scanner.nextLine());
            consumerService.deleteConsumer(id);
            System.out.println("Consumer #" + id + " deleted successfully.");
        } catch (InvalidInputException | ConsumerNotFoundException e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }

    private void listConsumers() {
        List<Consumer> all = consumerService.listAll();
        if (all.isEmpty()) {
            System.out.println("No consumers registered yet.");
            return;
        }
        System.out.printf("%-6s %-10s %-20s %-15s %-12s%n", "ID", "Type", "Name", "Phone", "Address");
        for (Consumer c : all) {
            System.out.println(c);
        }
        System.out.println("Total consumers: " + consumerService.totalConsumers());
    }

    private void generateBill() {
        try {
            System.out.print("Enter Consumer ID: ");
            int id = Validation.validateConsumerId(scanner.nextLine());
            Consumer consumer = consumerService.searchById(id);

            System.out.print("Previous meter reading: ");
            String previous = scanner.nextLine();
            System.out.print("Current meter reading: ");
            String current = scanner.nextLine();

            Bill bill = billingService.generateBill(consumer, previous, current);
            System.out.println(bill.buildReceipt());
        } catch (InvalidInputException | ConsumerNotFoundException e) {
            System.out.println("Billing failed: " + e.getMessage());
        }
    }

    private void backupNow() {
        FileManager.backupConsumers(consumerService.buildBackupDump());
        System.out.println("Backup started in the background...");
    }
}
