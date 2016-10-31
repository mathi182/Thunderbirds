package ca.ulaval.glo4002.thunderbird.boarding.domain.flight.exceptions;

import ca.ulaval.glo4002.thunderbird.reservation.exceptions.ElementNotFoundException;

import java.io.Serializable;

public class FlightNumberNotFoundException extends ElementNotFoundException implements Serializable {
    public FlightNumberNotFoundException(String flightNumber) {
        super("flight with number " + flightNumber);
    }
}
