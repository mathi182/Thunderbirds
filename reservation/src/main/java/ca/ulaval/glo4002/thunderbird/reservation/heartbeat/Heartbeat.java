package ca.ulaval.glo4002.thunderbird.reservation.heartbeat;

import com.fasterxml.jackson.annotation.JsonProperty;

class Heartbeat {

    @JsonProperty("token")
    private final String token;
    @JsonProperty("date")
    private final long date;

    Heartbeat(String token) {
        this.token = token;
        this.date = System.currentTimeMillis();
    }

}