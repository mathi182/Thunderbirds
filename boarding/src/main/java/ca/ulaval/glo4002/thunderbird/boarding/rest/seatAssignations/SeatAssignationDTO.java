package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SeatAssignationDTO {
    @JsonProperty("passenger_hash")
    public UUID passengerHash;

    @JsonProperty("mode")
    public String mode;

    @JsonProperty("seat_class")
    public Seat.SeatClass seatClass;

    public SeatAssignationDTO() {

    }
}
