package ca.ulaval.glo4002.thunderbird.boarding.domain;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.NotFoundException;
import java.util.HashMap;

public class FlightRepository {
        private static HashMap<String,String> flightRepository = new HashMap();
    private String[] modelSet = {"dash-8","a320","boeing-777-300"};

    public static void addFlight(String flightNumber) {
        //TODO Check if the flight was already registered before updating
        String planeModel = ""; //TODO Randomly select a plane model
        flightRepository.put(flightNumber,planeModel);
    }

    public static String getPlaneModel(String flightNumber) {
        try {
            return flightRepository.get(flightNumber);
        }
        catch (NotFoundException e) {
            throw new NotImplementedException(); //TODO Implement a Flight not found exception
        }
    }
}


