package simulation;

import automail.Charger;

public class StatisticsTracker {

    private int numDeliveredItem;
    private double totBillableActivity;
    private double totActivityCost;
    private double totServiceCost;
    private int totSuccessLookups;
    private int totFailureLookups;

    public StatisticsTracker() {
        this.numDeliveredItem = 0;
        this.totBillableActivity = 0;
        this.totActivityCost = 0;
        this.totServiceCost = 0;
        this.totSuccessLookups = 0;
        this.totFailureLookups = 0;
    }

    public void recordStatistic(Charger charger){
        this.numDeliveredItem = charger.getNumDeliveredItem();
        this.totBillableActivity = charger.getTotBillableActivity();
        this.totActivityCost = charger.getTotActivityCost();
        this.totServiceCost = charger.getTotServiceCost();
        this.totSuccessLookups = charger.getTotSuccessLookups();
        this.totFailureLookups = charger.getTotFailureLookups();
    }

    public void statisticsPrintOut() {
        System.out.format("The total number of items delivered: %d%n", numDeliveredItem);
        System.out.format("The total billable activity: %4.2f%n", totBillableActivity);
        System.out.format("The total activity cost: %4.2f%n", totActivityCost);
        System.out.format("The total service cost: %4.2f%n", totServiceCost);
        System.out.format("The total number of successful lookups: %d%n", totSuccessLookups);
        System.out.format("The total number of failed lookups: %d%n", totFailureLookups);
    }
}
