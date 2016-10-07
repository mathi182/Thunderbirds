package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exception.MissingInfoException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by alexandre on 2016-10-07.
 */
public class CheckinAssembler {
    public static final String SELF = "SELF";
    @JsonProperty("passenger_hash")
    public String passengerHash;

    @JsonProperty("by")
    public String agentId;

    @JsonIgnore
    public Checkin toDomain() {
        if(agentId == null){
            throw new MissingInfoException("by");
        }

        if (this.agentId.equals(SELF)) {
            return  new CheckinSelf(passengerHash);
        }
        else {
            return new Checkin(passengerHash, agentId);
        }
    }
}
