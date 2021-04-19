package automail;

/**
 * Charge exists in every mailItem.
 * It records the mail item's service fee, activity units, cost, and final charge.
 *
 * Written by Workshop16Team02 04/2021
 */

public class Charge {

    private double serviceFee;
    private double activity;
    private double cost;
    private double charge;

    /**
     * the constructor of Charge class, initiate it with all attributes needed
     * @param serviceFee service fee corresponding to the floor. Requested from WifiModem
     * @param activity the activity units of the item's mail item's delivery
     * @param cost raw cost of one mail item's delivery, calculated from serviceFee and activity
     * @param charge the final charge of one mail item's delivery, calculated from cost
     */
    public Charge(double serviceFee, double activity, double cost, double charge) {
        this.serviceFee = serviceFee;
        this.activity = activity;
        this.cost = cost;
        this.charge = charge;
    }

    /**
     *
     * @return the final charge of the mail item's delivery
     */
    public double getCharge() {
        return charge;
    }

    /**
     *
     * @param charge is the desired value of charge
     */
    public void setCharge(double charge) {
        this.charge = charge;
    }

    /**
     *
     * @return the cost of the mail item's delivery, calculated from serviceFee and activity
     */
    public double getCost() {
        return cost;
    }

    /**
     *
     * @param cost is the desired value of cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     *
     * @return the serviceFee of the mail item's delivery
     */
    public double getServiceFee() {
        return serviceFee;
    }

    /**
     *
     * @param serviceFee is the desired value of serviceFee
     */
    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    /**
     *
     * @return the activity units  of the mail item's delivery
     */
    public double getActivity() {
        return activity;
    }

    /**
     *
     * @param activity is the desired value of activity
     */
    public void setActivity(double activity) {
        this.activity = activity;
    }

    /**
     * This is a toString method of Charge
     * @return String. The final charge, the cost, the service fee, the activity units, and print them out in a clear format
     */
    @Override
    public String toString() {
        return String.format("| Charge: %4.2f | Cost: %4.2f | Fee: %4.2f | Activity: %4.2f", charge, cost, serviceFee, activity);
    }
}
