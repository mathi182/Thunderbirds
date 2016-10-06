package ca.ulaval.glo4002.thunderbird.boarding.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public enum FlightRepository {
    INSTANCE;

    private static final String DASH_FLIGHT_NUMBER = "QK-918";
    private static final String A320_FLIGHT_NUMBER = "NK-750";
    private static final String BOEING_FLIGHT_NUMBER = "QK-432";
    private static final String DASH_MODEL = "dash-8";
    private static final String A320_MODEL = "a320";
    private static final String BOEING_MODEL = "boeing-777-300";

    private Random generator = new Random();
    private static HashMap<String, String> flightModelRepository = new HashMap();
    private static HashMap<String, HashMap<String, List<String>>> flightAvailableSeatsRepository = new HashMap();
    private String[] modelSet = {"dash-8", "a320", "boeing-777-300"};

    private String addFlightModel(String flightNumber) {
        String planeModel = modelSet[generator.nextInt(3)];
        flightModelRepository.put(flightNumber, planeModel);
        return planeModel;
    }

    public String getPlaneModel(String flightNumber) {
        String planeModel = flightModelRepository.get(flightNumber);
        if (planeModel == null)
            planeModel = addFlightModel(flightNumber);
        return planeModel;
    }

    private List<String> getModelSeatsList(String planeModel) {
        List<String> listSeats = new ArrayList<>();
        //TODO Implement ca avec un GET vers http://glo3000.ift.ulaval.ca/plane-blueprint/planes/{planeModel}/seats.json
        return listSeats;
    }

    public List<String> getFlightAvailableSeats(String flightNumber, String flightDate) {
        HashMap<String, List<String>> flightMap = flightAvailableSeatsRepository.get(flightNumber);
        //If the flight was not already entered, create an empty one.
        if (flightMap == null) {
            flightMap = new HashMap<>();
            flightAvailableSeatsRepository.put(flightNumber, flightMap);
        }
        //If the flightDate was not already entered, create an empty one.
        List<String> availableSeatsList = flightMap.get(flightDate);
        if (availableSeatsList == null) {
            availableSeatsList = getModelSeatsList(flightModelRepository.get(flightNumber));
            flightMap.put(flightDate, availableSeatsList);
        }
        return availableSeatsList;
    }

    public boolean takeSeat(String flightNumber, String flightDate, String seatNumber) {
        List<String> availableSeatsList = getFlightAvailableSeats(flightNumber, flightDate);
        return availableSeatsList.remove(seatNumber);
    }

    public boolean contains(String flight_number) {
        return flightModelRepository.containsKey(flight_number);
    }

    public void clear() {
        flightModelRepository.clear();
    }

    public int size() {
        return flightModelRepository.size();
    }

    public void demoSetup() {
        flightModelRepository.put(BOEING_FLIGHT_NUMBER, BOEING_MODEL);
        flightModelRepository.put(DASH_FLIGHT_NUMBER, DASH_MODEL);
        flightModelRepository.put(A320_FLIGHT_NUMBER, A320_MODEL);
    }
}


