package ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions;

import ca.ulaval.glo4002.thunderbird.reservation.exceptions.ElementNotFoundException;

import java.io.Serializable;

public class PassengerNotFoundException extends ElementNotFoundException implements Serializable {
    public PassengerNotFoundException(String id)   {
        super("passenger with id " + id);
    }
}
