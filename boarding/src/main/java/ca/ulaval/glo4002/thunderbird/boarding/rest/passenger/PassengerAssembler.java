package ca.ulaval.glo4002.thunderbird.boarding.rest.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.time.Instant;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class PassengerAssembler {

    private static final String ECONOMY = "economy";
    private static final String BUSINESS = "business";

    private static final Seat.SeatClass DEFAULT_SEAT_CLASS = Seat.SeatClass.ANY;

    public Passenger toDomain(PassengerDTO passengerDTO){
        Seat.SeatClass seatClass = getSeatClassFromString(passengerDTO.seatClass);
        UUID passengerHash = UUID.fromString(passengerDTO.passengerHash);
        Instant flightDate = ISO_INSTANT.parse(passengerDTO.flightDate, Instant::from);
        String flightNumber = passengerDTO.flightNumber;

        return new Passenger(passengerHash,seatClass, flightDate, flightNumber);
    }

    private Seat.SeatClass getSeatClassFromString(String source){
        source = source.toLowerCase();
        Seat.SeatClass seatClass = DEFAULT_SEAT_CLASS;
        switch (source){
            case ECONOMY:
                seatClass = Seat.SeatClass.ECONOMY;
                break;
            case BUSINESS:
                seatClass = Seat.SeatClass.BUSINESS;
                break;
            //TODO implement an exception for invalid seat_class
        }

        return seatClass;
    }
}
