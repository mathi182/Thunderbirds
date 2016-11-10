package ca.ulaval.glo4002.thunderbird.reservation.passenger;

public class PassengerAssembler {
    public PassengerDTO toDTO(Passenger passenger) {
        PassengerDTO passengerDTO = new PassengerDTO();

        passengerDTO.passengerHash = passenger.getId().toString();
        passengerDTO.seatClass = passenger.getSeatClass();
        passengerDTO.flightNumber = passenger.getFlightNumber();
        passengerDTO.flightDate = passenger.getFlightDate().toString();

        return passengerDTO;
    }
}
