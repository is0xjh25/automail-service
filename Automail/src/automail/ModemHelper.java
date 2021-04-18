package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;
import simulation.Simulation;

public class ModemHelper {


    public ModemHelper(){

    }

    public double getServiceFee(int floor) {
        return Simulation.getwModem().forwardCallToAPI_LookupPrice(floor);
    }
}
