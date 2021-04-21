package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;
import simulation.Simulation;

/**
 * Modem Adapter is a tool for Charger to get given floor's service fee from the WifiModem.
 * It avoids direct interaction between Charger and WifiModem.
 *
 * Written by Workshop16-Team02 04/2021
 */
public class ModemAdapter {

    /**
     * Constructor of ModemAdapter
     */
    public ModemAdapter(){}

    /**
     * Get the corresponding service fee to the floor
     * @param floor the destination floor of the delivery
     * @return the service fee of the desired floor
     */
    public double getServiceFee(int floor) {
        return Simulation.getwModem().forwardCallToAPI_LookupPrice(floor);
    }
}
