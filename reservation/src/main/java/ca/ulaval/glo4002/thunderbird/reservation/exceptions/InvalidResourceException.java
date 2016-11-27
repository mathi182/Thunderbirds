package ca.ulaval.glo4002.thunderbird.reservation.exceptions;

import java.io.Serializable;

public class InvalidResourceException extends RuntimeException implements Serializable {
    public InvalidResourceException(String msg) {super(msg);}
}