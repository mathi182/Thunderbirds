package ca.ulaval.glo4002.thunderbird.boarding.application.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.time.Instant;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class PassengerAssembler {
    private static final String ECONOMY = "economy";
    private static final String BUSINESS = "business";

    public Passenger toDomain(PassengerDTO passengerDTO) {
        Seat.SeatClass seatClass = getSeatClassFromString(passengerDTO.seatClass);
        UUID passengerHash = passengerDTO.passengerHash;
        Instant flightDate = ISO_INSTANT.parse(passengerDTO.flightDate, Instant::from);
        String flightNumber = passengerDTO.flightNumber;
        Boolean isVip = passengerDTO.vip;
        Boolean isCheckedIn = passengerDTO.checkedIn;

        return new Passenger(passengerHash, seatClass, flightDate, flightNumber, isVip, isCheckedIn);
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