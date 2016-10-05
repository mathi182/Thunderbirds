package ca.ulaval.glo4002.thunderbird.boarding.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

public class SeatAssignations {

    String passengerHash;   //TODO Package-private to test. Make private afterward
    String mode;            //TODO Package-private to test. Make private afterward
    @JsonProperty("seat") String seat = "12-A";
    int id;

    @JsonCreator
    SeatAssignations(@JsonProperty("passenger_hash") String passengerHash,
                 @JsonProperty("mode") String mode) {
        this.passengerHash = passengerHash;
        this.mode = mode;
        Random r = new Random();
        this.id = r.nextInt(Integer.MAX_VALUE); //TODO : to change.
    }

    public int getId() {
        return id;
    }
}
