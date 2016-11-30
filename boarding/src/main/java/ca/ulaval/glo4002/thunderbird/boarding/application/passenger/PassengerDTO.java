package ca.ulaval.glo4002.thunderbird.boarding.application.passenger;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PassengerDTO {
    public UUID passengerHash;
    public String seatClass;
    public String flightNumber;
    public String flightDate;
    public Boolean vip;
    public Boolean checkin;

    @JsonCreator
    public PassengerDTO(@JsonProperty("passenger_hash") UUID passengerHash,
                        @JsonProperty("seat_class") String seatClass,
                        @JsonProperty("flight_date") String flightDate,
                        @JsonProperty("flight_number") String flightNumber,
                        @JsonProperty("vip") Boolean vip,
                        @JsonProperty("checkin") Boolean checkin) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightDate = flightDate;
        this.flightNumber = flightNumber;
        this.vip = vip;
        this.checkin = checkin;
    }
}
