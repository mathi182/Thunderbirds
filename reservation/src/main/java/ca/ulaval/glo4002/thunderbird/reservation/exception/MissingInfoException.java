package ca.ulaval.glo4002.thunderbird.reservation.exception;

/**
 * Created by alexandre on 2016-10-07.
 */
public class MissingInfoException extends RuntimeException {
    private String by;
    public MissingInfoException(String by) {
        this.by = by;
    }
}
