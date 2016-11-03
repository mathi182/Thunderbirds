package ca.ulaval.glo4002.thunderbird.boarding.domain.flight;

import java.util.Date;

public interface FlightRepository {
    Flight getFlight(String flightNumber, Date flightDate);

    void saveFlight(Flight flight);
}
