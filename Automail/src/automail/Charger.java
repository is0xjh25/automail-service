package automail;

import java.util.HashMap;

/**
 * Charger provides the function of estimating a delivery charge and calculating final charge.
 * It also stores configurable variable used for calculating charge,
 * and keeps track of the statistics of the whole delivery process.
 *
 * Written by Workshop16Team02 04/2021
 */
public class Charger {
    private static double ACTIVITY_UNIT_PRICE = 0.224;
    private static double MARKUP_PERCENTAGE = 0.059;
    private int numDeliveredItem;
    private int totSuccessLookups;
    private int totFailureLookups;
    private double totBillableActivity;
    private double totActivityCost;
    private double totServiceCost;
    // A hashmap  is created to store past service fees, in case a lookup failure happens
    private static HashMap<Integer, Double> serviceFeeRecords = new HashMap<>();
    private ModemAdapter modemAdapter = new ModemAdapter();

    /**
     * Constructor of Charger. It initialises all the statistics to 0.
     */
    public Charger() {
        this.numDeliveredItem = 0;
        this.totBillableActivity = 0;
        this.totActivityCost = 0;
        this.totServiceCost = 0;
        this.totSuccessLookups = 0;
        this.totFailureLookups = 0;
    }

    /**
     * Calculates the estimated charge of the item. Used for priority judgement.
     * @param mailItem The mail item that calculates the charge for.
     * @return A Charge object contains all related information.
     */
    public double initialCharge(MailItem mailItem) {
        // Calculate the charge
        double serviceFee = lookupServiceFee(mailItem.getDestFloor());
        double activity = mailItem.getDestFloor() * 5 * 2 + 0.1;
        double activityCost = activity * ACTIVITY_UNIT_PRICE;
        double cost = serviceFee + activityCost;
        double charge = cost * (1 + MARKUP_PERCENTAGE);

        // Set the Charge attributes to the calculated results
        mailItem.setCharge(new Charge(serviceFee, activity, cost, charge));

        return charge;
    }

    /**
     * Calculates the final charge of the item, which is presented to the client.
     * @param mailItem The mail item that calculates the charge for.
     */
    public void finalCharge(MailItem mailItem) {
        // Calculate the charge
        double serviceFee = lookupServiceFee(mailItem.getDestFloor());
        double activity =  mailItem.getCharge().getActivity();
        double activityCost = activity * ACTIVITY_UNIT_PRICE;
        double cost = serviceFee + activityCost;
        double charge = cost * (1 + MARKUP_PERCENTAGE);

        // Update the service fee, cost, and charge.
        mailItem.getCharge().setServiceFee(serviceFee);
        mailItem.getCharge().setCost(cost);
        mailItem.getCharge().setCharge(charge);

        // Record the statistics since the delivery is finished.
        totBillableActivity += activity;
        totActivityCost += activityCost;
        totServiceCost += serviceFee;
        numDeliveredItem++;
    }

    // Look up the service fee through modemHelper.
    private double lookupServiceFee (int floor) {
        double serviceFee = modemAdapter.getServiceFee(floor);

        if (serviceFee < 0 ) {
            totFailureLookups++;
            if (serviceFeeRecords.containsKey(floor)) {
                serviceFee = serviceFeeRecords.get(floor);
            } else {
                serviceFee = 0;
            }
        } else {
            totSuccessLookups++;
            serviceFeeRecords.put(floor, serviceFee);
        }

        return serviceFee;
    }

    public int getNumDeliveredItem() {
        return numDeliveredItem;
    }

    public void setNumDeliveredItem(int numDeliveredItem) {
        this.numDeliveredItem = numDeliveredItem;
    }

    public double getTotBillableActivity() {
        return totBillableActivity;
    }

    public void setTotBillableActivity(double totBillableActivity) {
        this.totBillableActivity = totBillableActivity;
    }

    public double getTotActivityCost() {
        return totActivityCost;
    }

    public void setTotActivityCost(double totActivityCost) {
        this.totActivityCost = totActivityCost;
    }

    public double getTotServiceCost() {
        return totServiceCost;
    }

    public void setTotServiceCost(double totServiceCost) {
        this.totServiceCost = totServiceCost;
    }

    public int getTotSuccessLookups() {
        return totSuccessLookups;
    }

    public void setTotSuccessLookups(int totSuccessLookups) {
        this.totSuccessLookups = totSuccessLookups;
    }

    public int getTotFailureLookups() {
        return totFailureLookups;
    }

    public void setTotFailureLookups(int totFailureLookups) {
        this.totFailureLookups = totFailureLookups;
    }
}

