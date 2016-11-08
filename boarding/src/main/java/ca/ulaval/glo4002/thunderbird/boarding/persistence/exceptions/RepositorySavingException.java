package ca.ulaval.glo4002.thunderbird.boarding.persistence.exceptions;


public class RepositorySavingException extends Exception {
    public RepositorySavingException(String reason)   {
        super(reason);
    }
}
