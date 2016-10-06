package ca.ulaval.glo4002.thunderbird.boarding.domain;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerStorage;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Random;

public class SeatAssignations {

    FlightRepository repository = FlightRepository.INSTANCE;
    String passengerHash;   //TODO Package-private to test. Make private afterward
    String mode;            //TODO Package-private to test. Make private afterward
    @JsonProperty("seat") String seat = "12-A";
    int id;

    @JsonCreator
    SeatAssignations(@JsonProperty("passenger_hash") String passengerHash,
                 @JsonProperty("mode") String mode) {
        this.passengerHash = passengerHash;
        this.mode = mode;
        Random r = new Random();
        this.id = r.nextInt(Integer.MAX_VALUE); //TODO : to change.
    }

    public int getId() {
        return id;
    }

    public String assignSeat() {
        PassengerStorage passenger = PassengerStorage.findByPassengerHash(passengerHash);
        int reservationNumber = passenger.getReservationNumber();
        Reservation passengerReservation = Reservation.findByReservationNumber(reservationNumber);
        String flightNumber = passengerReservation.getFlightNumber();
        String flightDate = passengerReservation.getFlightDate();

        List<String> availableSeatsList = repository.getFlightAvailableSeats(flightNumber,flightDate);
        //TODO selecttionner un siege libre au hasard (p-e avec une methode a soi pour permettre de supporter d'autre mode dans le futur
        String selectedSeat = "";   //TODO Le mettre la.
        boolean success = repository.takeSeat(flightNumber,flightDate,selectedSeat); //Ca va l'enlever de la liste d'available et retourner si l'operation etait possible
        return selectedSeat;
    }
}
