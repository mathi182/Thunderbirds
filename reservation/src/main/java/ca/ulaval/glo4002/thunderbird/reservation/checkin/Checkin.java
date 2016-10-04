package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exception.CheckinAlreadySavedException;
import ca.ulaval.glo4002.thunderbird.reservation.exception.CheckinNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.util.Strings;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.UUID;

public class Checkin {
    private static final HashMap<String, Checkin> checkinStore = new HashMap<>();
    private String checkinId;
    private String passengerHash;
    private String agentId;

    @JsonCreator
    public Checkin(@JsonProperty("passenger_hash") String passengerHash, @JsonProperty("by") String agentId) {
        this.checkinId = UUID.randomUUID().toString();
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

    public String getCheckinId() {
        return checkinId;
    }

    public synchronized void save() {
        if (checkinStore.containsKey(checkinId)) {
            throw new CheckinAlreadySavedException(checkinId);
        }
        checkinStore.put(this.checkinId, this);
    }

    public static synchronized Checkin findByCheckinId(String checkinId) {
        Checkin checkin = checkinStore.get(checkinId);
        if (checkin == null) {
            throw new CheckinNotFoundException(checkinId);
        }
        return checkin;
    }
}
