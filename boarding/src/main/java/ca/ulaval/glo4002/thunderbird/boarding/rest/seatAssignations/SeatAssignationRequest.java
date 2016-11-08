package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SeatAssignationRequest {
    @JsonProperty("passenger_hash")
    public UUID passengerHash;
    public String mode;
    public Seat.SeatClass seatClass;

    public SeatAssignationRequest() {

    }
}
