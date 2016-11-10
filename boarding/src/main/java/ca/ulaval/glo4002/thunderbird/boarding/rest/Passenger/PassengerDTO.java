package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PassengerDTO {
    public String passengerHash;
    public String seatClass;
    public String flightNumber;
    public Instant flightDate;

    @JsonCreator
    public PassengerDTO(String passengerHash, String seatClass, Instant flightDate, String flightNumber) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightDate = flightDate;
        this.flightNumber = flightNumber;
    }
}
