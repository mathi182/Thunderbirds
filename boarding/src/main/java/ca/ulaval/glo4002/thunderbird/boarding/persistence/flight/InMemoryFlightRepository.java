package ca.ulaval.glo4002.thunderbird.boarding.persistence.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;


public class InMemoryFlightRepository implements FlightRepository {
    private HashMap<FlightID, Flight> flights = new HashMap<>();
    private AMSSystem amsSystem;
    private PlaneService planeService;

    public InMemoryFlightRepository(AMSSystem amsSystem, PlaneService planeService) {
        this.amsSystem = amsSystem;
        this.planeService = planeService;
    }

    public Flight getFlight(String flightNumber, Instant flightDate) {
        FlightID flightID = new FlightID(flightNumber, flightDate);
        Flight flight = flights.get(flightID);
        if (flight == null) {
            flight = fetchFlight(flightNumber, flightDate);
        }
        return flight;
    }

    private Flight fetchFlight(String flightNumber, Instant flightDate) {
        String modelID = amsSystem.getPlaneModel(flightNumber);
        Plane plane = planeService.getPlaneInformation(modelID);
        List<Seat> seats = planeService.getSeats(modelID);
        return new Flight(flightNumber, flightDate, plane, seats);
    }

    @Override
    public void saveFlight(Flight flight) {
        FlightID flightID = new FlightID(flight.getFlightNumber(), flight.getFlightDate());
        flights.put(flightID, flight);
    }

    private class FlightID {
        private String flightNumber;
        private Instant flightDate;

        public FlightID(String flightNumber, Instant flightDate) {
            this.flightNumber = flightNumber;
            this.flightDate = flightDate;
        }
    }
}
