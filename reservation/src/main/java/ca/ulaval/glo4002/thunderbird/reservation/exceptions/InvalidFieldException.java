package ca.ulaval.glo4002.thunderbird.reservation.exceptions;

import javax.ws.rs.ext.Provider;

public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException(String field)   {
        super("field " + field + " is invalid");
    }
}
