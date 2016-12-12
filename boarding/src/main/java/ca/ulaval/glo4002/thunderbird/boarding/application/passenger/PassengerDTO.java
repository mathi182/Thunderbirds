package ca.ulaval.glo4002.thunderbird.boarding.application.passenger;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PassengerDTO {
    public UUID passengerHash;
    public String seatClass;
    public String flightNumber;
    public Instant flightDate;
    public Boolean vip;
    public Boolean checkedIn;
    public Boolean child;

    @JsonCreator
    public PassengerDTO(UUID passengerHash, String seatClass, Instant flightDate,
                        String flightNumber, Boolean vip, Boolean checkedIn, Boolean child) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightDate = flightDate;
        this.flightNumber = flightNumber;
        this.vip = vip;
        this.checkedIn = checkedIn;
        this.child = child;
    }
}
