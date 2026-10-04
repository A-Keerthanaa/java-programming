package model;


public class ResidentialConsumer extends Consumer {

    private static final double SLAB1_RATE = 2.0;   // 0-10 units
    private static final double SLAB2_RATE = 3.5;   // 11-20 units
    private static final double SLAB3_RATE = 5.0;   // above 20 units

    public ResidentialConsumer(String name, String address, String phone) {
        super(name, address, phone);
    }

    public ResidentialConsumer(int consumerId, String name, String address, String phone) {
        super(consumerId, name, address, phone);
    }

    @Override
    public double calculateBill(double unitsConsumed) {
        double amount = 0;
        double remaining = unitsConsumed;

        double slab1 = Math.min(remaining, 10);
        amount += slab1 * SLAB1_RATE;
        remaining -= slab1;

        if (remaining > 0) {
            double slab2 = Math.min(remaining, 10);
            amount += slab2 * SLAB2_RATE;
            remaining -= slab2;
        }

        if (remaining > 0) {
            amount += remaining * SLAB3_RATE;
        }

        return amount;
    }

    @Override
    public String getConsumerType() {
        return "Residential";
    }
}
