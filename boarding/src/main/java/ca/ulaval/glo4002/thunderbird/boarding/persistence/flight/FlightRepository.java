package ca.ulaval.glo4002.thunderbird.boarding.persistence.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;

import java.time.Instant;

public interface FlightRepository {
    Flight getFlight(String flightNumber, Instant flightDate);

    void saveFlight(Flight flight);
}
