package model;


public class CommercialConsumer extends Consumer {

    private static final double FLAT_RATE = 8.0;
    private static final double SERVICE_CHARGE = 50.0;

    public CommercialConsumer(String name, String address, String phone) {
        super(name, address, phone);
    }

    public CommercialConsumer(int consumerId, String name, String address, String phone) {
        super(consumerId, name, address, phone);
    }

    @Override
    public double calculateBill(double unitsConsumed) {
        return (unitsConsumed * FLAT_RATE) + SERVICE_CHARGE;
    }

    @Override
    public String getConsumerType() {
        return "Commercial";
    }
}
