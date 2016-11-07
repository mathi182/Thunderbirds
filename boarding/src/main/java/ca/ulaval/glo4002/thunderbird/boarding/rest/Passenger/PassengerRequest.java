package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PassengerRequest {
    public String passengerHash;
    public String seatClass;

    public PassengerRequest(@JsonProperty("passenger_hash")String passengerHash,
                            @JsonProperty("seat_class") String seatClass){
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
    }
}
