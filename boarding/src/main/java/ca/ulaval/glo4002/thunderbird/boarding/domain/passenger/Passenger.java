package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Passenger {
    @Id
    private UUID passengerHash;
    @Column
    private Seat.SeatClass seatClass;
    @Column
    private Instant flightDate;
    @Column
    private String flightNumber;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "passenger")
    @JsonManagedReference
    private List<Baggage> baggages;

    public Passenger() {
        //for hibernate
    }

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate, String flightNumber, List<Baggage> baggages) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.baggages = baggages;
        for (Baggage baggage : baggages) {
            baggage.setPassenger(this);
        }
    }

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate, String flightNumber){
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.baggages = new ArrayList<>();
    }

    public UUID getHash() {
        return passengerHash;
    }

    public boolean isSameSeatClass(Seat.SeatClass seatClass){
        return this.seatClass.equals(seatClass);
    }
}
