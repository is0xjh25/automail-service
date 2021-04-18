package simulation;

import automail.Automail;
import automail.Charger;
import automail.MailPool;
import automail.Robot;

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

    public void recordStatistic(Automail automail){
        MailPool mailPool = automail.mailPool;
        Robot[] robots = automail.robots;
        for (Robot robot : robots){
            Charger charger = robot.getCharger();
            numDeliveredItem+= charger.getNumDeliveredItem();
            totBillableActivity+= charger.getTotBillableActivity();
            totActivityCost+= charger.getTotActivityCost();
            totServiceCost+= charger.getTotServiceCost();
            totSuccessLookups+= charger.getTotSuccessLookups();
            totFailureLookups+= charger.getTotFailureLookups();
        }
        Charger centralCharger = mailPool.getCharger();
        totSuccessLookups+= centralCharger.getTotSuccessLookups();
        totFailureLookups+= centralCharger.getTotFailureLookups();
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
