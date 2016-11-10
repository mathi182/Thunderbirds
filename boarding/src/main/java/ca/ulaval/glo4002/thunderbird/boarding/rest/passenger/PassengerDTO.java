package ca.ulaval.glo4002.thunderbird.boarding.rest.passenger;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PassengerDTO {
    public String passengerHash;
    public String seatClass;
    public String flightNumber;
    public Instant flightDate;

    public PassengerDTO(@JsonProperty("passenger_hash")String passengerHash,
                        @JsonProperty("seat_class") String seatClass,
                        @JsonProperty("flight_date") Instant flightDate,
                        @JsonProperty("flight_number") String flightNumber){
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightDate = flightDate;
        this.flightNumber = flightNumber;
    }
}
