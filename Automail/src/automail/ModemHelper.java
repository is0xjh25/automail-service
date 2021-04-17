package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;

public class ModemHelper {

    private WifiModem wModem = null;

    public ModemHelper(WifiModem wModem) {
        this.wModem = wModem;
    }

    public double getServiceFee(int floor) {
        return wModem.forwardCallToAPI_LookupPrice(floor);
    }
}
