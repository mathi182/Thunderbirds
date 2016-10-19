package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exception.MissingInfoException;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckinAssembler {
    private static final String SELF = "SELF";
    @JsonProperty("passenger_hash")
    public String passengerHash;

    @JsonProperty("by")
    public String agentId;

    @JsonIgnore
    public CheckinAgentId toDomain() {
        if (Strings.isNullOrEmpty(agentId)) {
            throw new MissingInfoException("by");
        } else if (Strings.isNullOrEmpty(passengerHash)) {
            throw new MissingInfoException("passenger_hash");
        }

        if (this.agentId.equals(SELF)) {
            return new CheckinSelf(passengerHash);
        }

        return new CheckinAgentId(passengerHash, agentId);
    }
}
