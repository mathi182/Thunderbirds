package ca.ulaval.glo4002.thunderbird.boarding.domain;

import java.util.HashMap;
import java.util.Random;

public class FlightRepository {

    private static final String DASH_FLIGHT_NUMBER = "QK-918";
    private static final String A320_FLIGHT_NUMBER = "NK-750";
    private static final String BOEING_FLIGHT_NUMBER = "QK-432";
    private static final String DASH_MODEL = "dash-8";
    private static final String A320_MODEL = "a320";
    private static final String BOEING_MODEL = "boeing-777-300";

    private Random generator = new Random();
    private static HashMap<String,String> flightRepository = new HashMap();
    private String[] modelSet = {"dash-8","a320","boeing-777-300"};

    public void addFlight(String flightNumber) {
        if (flightRepository.containsKey(flightNumber)) {
            //Do nothing (Should we throw something?)
        }
        else {
            String planeModel = modelSet[generator.nextInt(3)];
            flightRepository.put(flightNumber,planeModel);
        }
    }

    public String getPlaneModel(String flightNumber) {
            return flightRepository.get(flightNumber);
    }

    public boolean contains(String flight_number) {
        return flightRepository.containsKey(flight_number);
    }

    public void clear() {
        flightRepository.clear();
    }

    public int size() {
        return flightRepository.size();
    }

    public void demoSetup() {
        flightRepository.put(BOEING_FLIGHT_NUMBER,BOEING_MODEL);
        flightRepository.put(DASH_FLIGHT_NUMBER,DASH_MODEL);
        flightRepository.put(A320_FLIGHT_NUMBER,A320_MODEL);
    }
}


