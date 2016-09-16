package ca.ulaval.glo4002.thunderbird.reservation;

public class Heartbeat {
    public final String token;
    public final long date;

    public Heartbeat(String token) {
        this.token = token;
        this.date = System.currentTimeMillis();
    }
}