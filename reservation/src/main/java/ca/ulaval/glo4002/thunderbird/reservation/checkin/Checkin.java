package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Checkin {
    private String passengerHash;
    private String agentId;

    @JsonCreator
    public Checkin(@JsonProperty("passenger_hash") String passengerHash, @JsonProperty("by") String agentId) {
        this.passengerHash = passengerHash;
        this.agentId = agentId;
    }

    public boolean isAgentIdValid(){
        return !(Strings.isNullOrEmpty(agentId));
    }

    public boolean isPassengerHashValid(){
        return !(Strings.isNullOrEmpty(passengerHash));
    }

    public String getPassengerHash() {
        return passengerHash;
    }
}
