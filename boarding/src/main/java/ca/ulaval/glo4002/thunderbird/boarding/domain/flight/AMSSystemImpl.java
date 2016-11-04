package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import java.util.HashMap;
import java.util.Random;

public class AMSSystemImpl implements AMSSystem {
    public static final String DASH_8 = "dash-8";
    public static final String AIRBUS_A320 = "a320";
    public static final String BOEING_777_300 = "boeing-777-300";
    private static final String[] modelSet = {DASH_8, AIRBUS_A320, BOEING_777_300};
    private static final String DASH_FLIGHT = "QK-918";
    private static final String AIRBUS_FLIGHT = "NK-750";
    private static final String BOEING_FLIGHT = "QK-432";
    private Random random = new Random();
    private HashMap<String, String> flightModelCache = new HashMap<>();

    @Override
    public synchronized String getPlaneModel(String flightNumber) {
        String planeModel;
        switch (flightNumber) {
            case DASH_FLIGHT:
                planeModel = DASH_8;
                break;
            case AIRBUS_FLIGHT:
                planeModel = AIRBUS_A320;
                break;
            case BOEING_FLIGHT:
                planeModel = BOEING_777_300;
                break;
            default:
                planeModel = flightModelCache.get(flightNumber);
                if (planeModel == null) {
                    int planeModelNumber = random.nextInt(modelSet.length);
                    planeModel = modelSet[planeModelNumber];
                    flightModelCache.put(flightNumber, planeModel);
                }
                break;
        }

        return planeModel;
    }
}
