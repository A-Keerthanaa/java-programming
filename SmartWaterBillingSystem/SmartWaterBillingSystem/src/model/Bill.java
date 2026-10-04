package model;

import java.time.LocalDate;


public class Bill {

    private final Consumer consumer;
    private final double unitsConsumed;
    private final double amount;
    private final LocalDate dueDate;
    private boolean paid;

    public Bill(Consumer consumer, double unitsConsumed, double amount) {
        this.consumer = consumer;
        this.unitsConsumed = unitsConsumed;
        this.amount = amount;
        this.dueDate = LocalDate.now().plusDays(15);
        this.paid = false;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void markPaid() {
        this.paid = true;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String buildReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("======================================\n");
        sb.append("        SMART WATER BILLING SYSTEM\n");
        sb.append("======================================\n");
        sb.append("Consumer ID   : ").append(consumer.getConsumerId()).append('\n');
        sb.append("Name          : ").append(consumer.getName()).append('\n');
        sb.append("Type          : ").append(consumer.getConsumerType()).append('\n');
        sb.append("Units Consumed: ").append(unitsConsumed).append('\n');
        sb.append("Amount Due    : Rs. ").append(String.format("%.2f", amount)).append('\n');
        sb.append("Due Date      : ").append(dueDate).append('\n');
        sb.append("Status        : ").append(paid ? "PAID" : "PENDING").append('\n');
        sb.append("======================================\n");
        return sb.toString();
    }
}
