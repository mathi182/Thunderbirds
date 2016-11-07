package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PassengerTestDTO {

    public String passengerHash;
    public String first_name;
    public String last_name;
    public String passport_number;
    public String seatClass;
    public String child;

    PassengerTestDTO(@JsonProperty("first_name") String firstName,
                     @JsonProperty("last_name") String lastName,
                     @JsonProperty("passport_number") String passportNumber,
                     @JsonProperty("seat_class") String seatClass,
                     @JsonProperty("passenger_hash") String passengerHash,
                     @JsonProperty("child") String child){
        this.passengerHash = passengerHash;
        this.first_name = firstName;
        this.last_name = lastName;
        this.passport_number = passportNumber;
        this.seatClass = seatClass;
        this.child = child;
    }

}
