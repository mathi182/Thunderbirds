package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PassengerTestRequest {

    public final String passenger_Hash;
    public final String first_name;
    public final String last_name;
    public final String passport_number;
    public final String seat_Class;
    public final String child;

    PassengerTestRequest(@JsonProperty("first_name") String firstName,
                         @JsonProperty("last_name") String lastName,
                         @JsonProperty("passport_number") String passportNumber,
                         @JsonProperty("seat_class") String seatClass,
                         @JsonProperty("passenger_hash") String passengerHash,
                         @JsonProperty("child") String child){
        this.passenger_Hash = passengerHash;
        this.first_name = firstName;
        this.last_name = lastName;
        this.passport_number = passportNumber;
        this.seat_Class = seatClass;
        this.child = child;
    }

}
