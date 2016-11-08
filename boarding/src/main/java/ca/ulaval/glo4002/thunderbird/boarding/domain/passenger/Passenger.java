package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Passenger {
    @Id
    private UUID passengerHash;
    @Column
    private Seat.SeatClass seatClass;

    public Passenger() {
    }

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
