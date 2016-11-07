package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.UUID;

public class Passenger {
    private UUID passengerHash;
    private Seat.SeatClass seatClass;

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass){
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
    }

    public UUID getHash() {
        return passengerHash;
    }

    public boolean isSameSeatClass(Seat.SeatClass seatClass){
        return this.seatClass.equals(seatClass);
    }
}
