package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SeatAssignationRequest {
    @JsonProperty("passenger_hash")
    public UUID passengerHash;
    public String mode;

    public SeatAssignationRequest() {

    }
}
