package ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions;

import ca.ulaval.glo4002.thunderbird.reservation.exceptions.ElementNotFoundException;

import java.io.Serializable;

public class ReservationNotFoundException extends ElementNotFoundException implements Serializable {
    public ReservationNotFoundException(String id) {
        super("reservation with id " + id);
    }
}
