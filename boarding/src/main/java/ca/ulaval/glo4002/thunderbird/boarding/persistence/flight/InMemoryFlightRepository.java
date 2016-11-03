package ca.ulaval.glo4002.thunderbird.boarding.persistence.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class InMemoryFlightRepository implements FlightRepository {
    private class FlightID {
        private String flightNumber;
        private Date flightDate;

        public FlightID(String flightNumber, Date flightDate) {
            this.flightNumber = flightNumber;
            this.flightDate = flightDate;
        }
    }

    private HashMap<FlightID, Flight> flights = new HashMap<>();
    private AMSSystem amsSystem;
    private PlaneRepository planeRepository;

    public InMemoryFlightRepository(AMSSystem amsSystem, PlaneRepository planerepository) {
        this.amsSystem = amsSystem;
        this.planeRepository = planerepository;
    }

    public Flight getFlight(String flightNumber, Date flightDate) {
        FlightID flightID = new FlightID(flightNumber, flightDate);
        Flight flight = flights.get(flightID);
        if (flight == null) {
            flight = fetchFlight(flightNumber, flightDate);
        }
        return flight;
    }

    private Flight fetchFlight(String flightNumber, Date flightDate) {
        String modelID = amsSystem.getPlaneModel(flightNumber);
        Plane plane = planeRepository.getPlaneInformation(modelID);
        List<Seat> seats = planeRepository.getSeats(modelID);
        Flight flight = new Flight(flightNumber, flightDate, plane, seats);
        return flight;
    }

    @Override
    public void saveFlight(Flight flight) {
        FlightID flightID = new FlightID(flight.getFlightNumber(), flight.getFlightDate());
        flights.put(flightID, flight);
    }
}
