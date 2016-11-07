package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.ElementNotFoundException;

import java.io.Serializable;
import java.util.UUID;

public class PassengerNotFoundException extends ElementNotFoundException implements Serializable {
    public PassengerNotFoundException(UUID id) {
        super("passenger with id " + id);
    }
}
