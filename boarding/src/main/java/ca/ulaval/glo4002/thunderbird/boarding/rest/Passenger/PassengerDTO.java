package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PassengerDTO {
    public String passengerHash;
    public String seatClass;

    public PassengerDTO(@JsonProperty("passenger_hash")String passengerHash,
                        @JsonProperty("seat_class") String seatClass){
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
    }
}
