package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;


import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.UUID;

public class PassengerAssembler {

    private static final String ECONOMY = "economy";

    private static final Seat.SeatClass DEFAULT_SEAT_CLASS = Seat.SeatClass.ANY;

    public Passenger toDomain(PassengerRequest passengerRequest){
        Seat.SeatClass seatClass = getSeatClassFromString(passengerRequest.seatClass);
        UUID passengerHash = UUID.fromString(passengerRequest.passengerHash);

        return new Passenger(passengerHash,seatClass);
    }

    private Seat.SeatClass getSeatClassFromString(String source){
        Seat.SeatClass seatClass = DEFAULT_SEAT_CLASS;
        switch (source){
            case ECONOMY:
                seatClass = Seat.SeatClass.ECONOMY;
        }

        return seatClass;
    }
}
