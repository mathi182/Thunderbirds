package ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions;

import java.io.Serializable;

public class PassengerAlreadyCheckedInException extends RuntimeException implements Serializable {
    public PassengerAlreadyCheckedInException(String id)   {
        super("passenger with id " + id + "already checked in");
    }
}
