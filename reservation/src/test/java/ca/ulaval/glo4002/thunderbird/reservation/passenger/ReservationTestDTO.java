package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexisLessard on 2016-11-06.
 */
public class ReservationTestDTO
{
    public final int reservationNumber;
    public final String flightDate;
    public final String flightNumber;
    public final List<PassengerTestDTO> passengers;

    public ReservationTestDTO(@JsonProperty("reservation_number") int reservationNumber,
                              @JsonProperty("flight_date") String flightDate,
                              @JsonProperty("flight_number") String flightNumber,
                              @JsonProperty("passengers") ArrayList<PassengerTestDTO> passengers){
        this.reservationNumber = reservationNumber;
        this.flightDate = flightDate;
        this.flightNumber = flightNumber;
        this.passengers = passengers;
    }
}
