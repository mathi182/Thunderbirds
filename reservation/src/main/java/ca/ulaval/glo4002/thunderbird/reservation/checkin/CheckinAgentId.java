package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;

import java.util.HashMap;
import java.util.UUID;

public class CheckinAgentId extends Checkin {
    public CheckinAgentId(String passengerHash, String agentId) {
        super(passengerHash, agentId);
    }
}
