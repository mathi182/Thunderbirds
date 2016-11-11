package ca.ulaval.glo4002.thunderbird.boarding.rest.passenger;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PassengerDTO {
    public String passengerHash;
    public String seatClass;
    public String flightNumber;
    public String flightDate;

    @JsonCreator
    public PassengerDTO(@JsonProperty("passenger_hash") String passengerHash,
                        @JsonProperty("seat_class") String seatClass,
                        @JsonProperty("flight_date") String flightDate,
                        @JsonProperty("flight_number") String flightNumber) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightDate = flightDate;
        this.flightNumber = flightNumber;
    }
}
