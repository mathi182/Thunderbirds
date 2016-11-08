package ca.ulaval.glo4002.thunderbird.reservation.exceptions;

public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException(String field)   {
        super("field " + field + " is invalid");
    }
}