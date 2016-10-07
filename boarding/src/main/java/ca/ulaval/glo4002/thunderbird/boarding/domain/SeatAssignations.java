package ca.ulaval.glo4002.thunderbird.boarding.domain;

import ca.ulaval.glo4002.thunderbird.boarding.exception.SeatNotAvailableException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerStorage;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeatAssignations {

    FlightRepository repository = FlightRepository.INSTANCE;
    private String passengerHash;
    private String mode;
    @JsonProperty("seat") String seat;
    int id;

    @JsonCreator
    SeatAssignations(@JsonProperty("passenger_hash") String passengerHash,
                     @JsonProperty("mode") String mode) {
        this.passengerHash = passengerHash;
        this.mode = mode;
        this.id = new Random().nextInt(Integer.MAX_VALUE);
    }

    public int getId() {
        return id;
    }

    public String assignSeat() {
        PassengerStorage passenger = PassengerStorage.findByPassengerHash(passengerHash);
        String flightNumber = passenger.getFlightNumber();
        String flightDate = passenger.getFlightDate();

        List<String> availableSeatsList = repository.getFlightAvailableSeats(flightNumber, flightDate);
        seat = availableSeatsList.get(new Random().nextInt(availableSeatsList.size()));
        boolean success = repository.takeSeat(flightNumber, flightDate, seat);

        if (!success)
            throw new SeatNotAvailableException(flightNumber, flightDate, seat);

        return seat;
    }
}
