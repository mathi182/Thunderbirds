package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created agentId alexandre on 2016-09-17.
 */
public class Checkin {
    public String passengerHash;
    public String agentId;

    @JsonCreator
    public Checkin(@JsonProperty("passenger_hash") String passengerHash, @JsonProperty("by") String agentId) {
        this.passengerHash = passengerHash;
        this.agentId = agentId;
    }
}
