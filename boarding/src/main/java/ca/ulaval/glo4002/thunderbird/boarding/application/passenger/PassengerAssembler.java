package ca.ulaval.glo4002.thunderbird.boarding.application.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.time.Instant;
import java.util.UUID;

public class PassengerAssembler {
    private static final String ECONOMY = "economy";
    private static final String BUSINESS = "business";

    public Passenger toDomain(PassengerDTO passengerDTO) {
        Seat.SeatClass seatClass = getSeatClassFromString(passengerDTO.seatClass);
        UUID passengerHash = passengerDTO.passengerHash;
        Instant flightDate = passengerDTO.flightDate;
        String flightNumber = passengerDTO.flightNumber;
        Boolean isVip = passengerDTO.vip;
        Boolean isCheckedIn = passengerDTO.checkedIn;
        Boolean isAChild = passengerDTO.child;

        return new Passenger(passengerHash, seatClass, flightDate, flightNumber, isVip, isCheckedIn, isAChild);
    }

    private Seat.SeatClass getSeatClassFromString(String source) {
        source = source.toLowerCase();
        switch (source) {
            case ECONOMY:
                return Seat.SeatClass.ECONOMY;
            case BUSINESS:
                return Seat.SeatClass.BUSINESS;
            default:
                return Seat.SeatClass.ANY;

        }
    }
}