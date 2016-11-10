package ca.ulaval.glo4002.thunderbird.reservation.passenger;

public class PassengerAssembler {
    public PassengerDTO toDTO(Passenger passenger) {
        PassengerDTO passengerDTO = new PassengerDTO();

        passengerDTO.passenger_hash = passenger.getId().toString();
        passengerDTO.seat_class = passenger.getSeatClass();
        passengerDTO.flight_number = passenger.getFlightNumber();
        passengerDTO.flight_date = passenger.getFlightDate().toString();

        return passengerDTO;
    }
}
