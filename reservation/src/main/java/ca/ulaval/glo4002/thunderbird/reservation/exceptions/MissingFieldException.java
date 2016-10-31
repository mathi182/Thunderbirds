package ca.ulaval.glo4002.thunderbird.reservation.exceptions;

import javax.ws.rs.ext.Provider;

public class MissingFieldException extends RuntimeException {
    public MissingFieldException(String field)   {
        super("field " + field + " required");
    }
}
