package automail;

import java.util.HashMap;

/**
 * Charger provides the function of estimating a delivery charge and calculating final charge.
 * It also stores configurable variable used for calculating charge,
 * and keeps track of the statistics of the whole delivery process.
 *
 * Written by Workshop16-Team02 04/2021
 */
public class Charger {
    // Configurable parameters for charge calculating
    private static double ACTIVITY_UNIT_PRICE = 0.224;
    private static double MARKUP_PERCENTAGE = 0.059;

    // Default value for lookup service fee activity
    private static double LOOKUP_FEE_ACTIVITY = 0.1;
    private int numDeliveredItem;
    private int totSuccessLookups;
    private int totFailureLookups;
    private double totBillableActivity;
    private double totActivityCost;
    private double totServiceCost;
    // A hashmap  is created to store past service fees, in case a lookup failure happens
    private static HashMap<Integer, Double> serviceFeeRecords = new HashMap<>();
    // Used to look up the service fee from the Wifi Modem
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
        // When calculating, subtract the destination floor by 1 to simulate counting from 1st floor
        double activity = (mailItem.getDestFloor() - 1) * 5 * 2 + LOOKUP_FEE_ACTIVITY;
        // Add the lookup fee activity no matter what the lookup result is
        totBillableActivity += LOOKUP_FEE_ACTIVITY;
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

        // Handles lookup success and failure
        // If the lookup fails, search the service fee records to use the last value
        if (serviceFee < 0 ) {
            totFailureLookups++;
            if (serviceFeeRecords.containsKey(floor)) {
                serviceFee = serviceFeeRecords.get(floor);
            } else {
                // If the destination floor is not in the records, then set the service fee to 0 to prevent overcharging
                serviceFee = 0;
            }
        } else {
            totSuccessLookups++;
            serviceFeeRecords.put(floor, serviceFee);
        }

        return serviceFee;
    }

    /**
     *
     * @return the number of delivered item
     */
    public int getNumDeliveredItem() {
        return numDeliveredItem;
    }

    /**
     *
     * @return get the total billable activity units
     */
    public double getTotBillableActivity() {
        return totBillableActivity;
    }

    /**
     *
     * @return get the total activity cost
     */
    public double getTotActivityCost() {
        return totActivityCost;
    }

    /**
     *
     * @return get total service cost
     */
    public double getTotServiceCost() {
        return totServiceCost;
    }

    /**
     *
     * @return get the total success lookups
     */
    public int getTotSuccessLookups() {
        return totSuccessLookups;
    }


    /**
     *
     * @return get the total failure lookups
     */
    public int getTotFailureLookups() {
        return totFailureLookups;
    }

}

