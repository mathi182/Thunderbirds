package ca.ulaval.glo4002.thunderbird.reservation.exceptions;

public class MissingFieldException extends RuntimeException {
    public MissingFieldException(String field)   {
        super("field " + field + " required");
    }
}
