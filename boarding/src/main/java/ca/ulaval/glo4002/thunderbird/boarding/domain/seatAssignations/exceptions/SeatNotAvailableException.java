package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.exceptions;

import java.io.Serializable;

public class SeatNotAvailableException extends RuntimeException implements Serializable {
    public SeatNotAvailableException(String flightNumber)  {
        super("seat not available on flight with number " + flightNumber);
    }
}
