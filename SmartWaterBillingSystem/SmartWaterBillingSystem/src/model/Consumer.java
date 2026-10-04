package model;


public abstract class Consumer implements Billable {

    private static int idCounter = 1000;

    private final int consumerId;
    private String name;
    private String address;
    private String phone;

    protected Consumer(String name, String address, String phone) {
        this.consumerId = ++idCounter;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    protected Consumer(int consumerId, String name, String address, String phone) {
        this.consumerId = consumerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        if (consumerId > idCounter) {
            idCounter = consumerId;
        }
    }

    public int getConsumerId() {
        return consumerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public abstract double calculateBill(double unitsConsumed);

    public abstract String getConsumerType();

    @Override
    public String toString() {
        return String.format("%-6d %-10s %-20s %-15s %-12s",
                consumerId, getConsumerType(), name, phone, address);
    }

    public String toDataLine() {
        return consumerId + "|" + getConsumerType() + "|" + name + "|" + phone + "|" + address;
    }
}