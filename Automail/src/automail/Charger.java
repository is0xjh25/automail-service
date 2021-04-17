package automail;

import java.util.HashMap;

public class Charger {
    private static double ACTIVITY_UNIT_PRICE = 0.224;
    private static double MARKUP_PERCENTAGE = 0.059;
    private int numDeliveredItem;
    private int totSuccessLookups;
    private int totFailureLookups;
    private double totBillableActivity;
    private double totActivityCost;
    private double totServiceCost;
    private HashMap<Integer, Double> serviceFeeRecords;
    private ModemHelper modemHelper;

    public Charger(ModemHelper modemHelper) {
        this.modemHelper = modemHelper;
        this.numDeliveredItem = 0;
        this.totBillableActivity = 0;
        this.totActivityCost = 0;
        this.totServiceCost = 0;
        this.totSuccessLookups = 0;
        this.totFailureLookups = 0;
        this.serviceFeeRecords = new HashMap<>();
    }

    public double initialCharge(MailItem mailItem) {
        double serviceFee = lookupServiceFee(mailItem.getDestFloor());
        double activity = mailItem.getDestFloor() * 5 * 2 + 0.1;
        double activityCost = activity * ACTIVITY_UNIT_PRICE;
        double cost = serviceFee + activityCost;
        double charge = cost * (1 + MARKUP_PERCENTAGE);

        mailItem.setCharge(new Charge(serviceFee, activityCost, cost, charge));

        return charge;
    }

    public void finalCharge(MailItem mailItem) {
        double serviceFee = lookupServiceFee(mailItem.getDestFloor());
        double activity =  mailItem.getCharge().getActivity();
        double activityCost = activity * ACTIVITY_UNIT_PRICE;
        double cost = serviceFee + activityCost;
        double charge = cost * (1 + MARKUP_PERCENTAGE);

        mailItem.getCharge().setServiceFee(serviceFee);
        mailItem.getCharge().setCost(cost);
        mailItem.getCharge().setCharge(charge);

        totBillableActivity += activity;
        totActivityCost += activityCost;
        totServiceCost += serviceFee;
        numDeliveredItem++;
    }

    private double lookupServiceFee (int floor) {
        double serviceFee = modemHelper.getServiceFee(floor);

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

