package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.PassengerBaggagesCollection;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
public class Passenger {
    public static final float VIP_DISCOUNT = 0.95f;

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID passengerHash;
    private Seat.SeatClass seatClass;
    private boolean isVip;
    private boolean isCheckedIn;
    private boolean isChild;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PassengerBaggagesCollection baggagesCollection;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Flight flight;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, boolean isVip,
                     boolean isCheckedIn, boolean isChild, Flight flight) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.isVip = isVip;
        this.isCheckedIn = isCheckedIn;
        this.isChild = isChild;
        this.flight = flight;
        this.baggagesCollection = new PassengerBaggagesCollection(this);
    }

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, boolean isVip, boolean isCheckedIn,
                     boolean isChild, Flight flight, PassengerBaggagesCollection baggagesCollection) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.isVip = isVip;
        this.isCheckedIn = isCheckedIn;
        this.isChild = isChild;
        this.flight = flight;
        this.baggagesCollection = baggagesCollection;
    }

    protected Passenger() {
        //for hibernate
    }

    public UUID getHash() {
        return passengerHash;
    }

    public Seat.SeatClass getSeatClass() {
        return seatClass;
    }

    public boolean isVip() {
        return isVip;
    }

    public boolean isChild() {
        return isChild;
    }

    public float calculateBaggagesPrice() {
        float price = baggagesCollection.calculateTotalPrice();
        return isVip ? price * VIP_DISCOUNT : price;
    }

    public void addBaggage(Baggage baggage) {
        baggagesCollection.addBaggage(baggage);
    }

    public Set<Baggage> getBaggages() {
        return baggagesCollection.getBaggages();
    }

    public Flight getFlight() {
        return flight;
    }

    public Seat getSeat() {
        return seat;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setBaggagesCollection(PassengerBaggagesCollection passengerBaggagesCollection) {
        baggagesCollection = passengerBaggagesCollection;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    public void setCheckedIn() {
        isCheckedIn = true;
    }

    public void assignSeat(SeatAssignationStrategy strategy) {
        seat = flight.reserveSeat(strategy, this);
    }
}