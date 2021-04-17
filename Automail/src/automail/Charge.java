package automail;

public class Charge {

    private double serviceFee;
    private double activityCost;
    private double cost;
    private double charge;

    public Charge(double serviceFee, double activityCost, double cost, double charge) {
        this.serviceFee = serviceFee;
        this.activityCost = activityCost;
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

    public double getActivityCost() {
        return activityCost;
    }

    public void setActivityCost(double activityCost) {
        this.activityCost = activityCost;
    }

    @Override
    public String toString() {
        return "| Charge: " + charge +
                "| Cost: " + cost +
                "| Fee: " + serviceFee +
                "| Activity:" + activityCost;
    }
}
