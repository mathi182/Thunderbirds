package ca.ulaval.glo4002.thunderbird.reservation.exception;

public class MissingInfoException extends RuntimeException {
    private String by;
    public MissingInfoException(String by) {
        this.by = by;
    }
}
