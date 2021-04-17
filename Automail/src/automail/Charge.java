package automail;

public class Charge {

    private double serviceFee;
    private double activity;
    private double cost;
    private double charge;

    public Charge(double serviceFee, double activity, double cost, double charge) {
        this.serviceFee = serviceFee;
        this.activity = activity;
        this.cost = cost;
        this.charge = charge;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public double getActivity() {
        return activity;
    }

    public void setActivity(double activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return String.format("| Charge: %4.2f | Cost: %4.2f | Fee: %4.2f | Activity: %4.2f", charge, cost, serviceFee, activity);
    }
}
