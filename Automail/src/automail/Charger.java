package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;

import java.util.HashMap;

public class Charger {
    private static double ACTIVITY_UNIT_PRICE = 0.224;
    private static double MARKUP_PERCENTAGE = 0.059;
    private double totActivity;
    private double totActivityCost;
    private double totServiceCost;
    private int totSuccessLookups;
    private int totFailureLookups;
    private HashMap<Integer, Double> serviceFeeRecords;
    private ModemHelper modemHelper;

    public Charger(ModemHelper modemHelper) {
        this.modemHelper = modemHelper;
        this.totActivity = 0;
        this.totServiceCost = 0;
        this.totServiceCost = 0;
        this.totSuccessLookups = 0;
        this.totFailureLookups = 0;
    }

    public double initialCharge(MailItem mailItem) {
        double serviceFee = lookupServiceFee(mailItem.getDestFloor());
        double activityCost = (mailItem.getDestFloor() * 2 + 1 + 0.1) * ACTIVITY_UNIT_PRICE;
        double cost = serviceFee + activityCost;
        double charge = cost * (1 + MARKUP_PERCENTAGE);
        mailItem.setCharge(new Charge(serviceFee, activityCost, cost, charge));
        return charge;
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


}

