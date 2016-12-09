package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggages;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggagesFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategy;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CheckedBaggages checkedBaggages;

    @ManyToOne(fetch = FetchType.LAZY)
    private Flight flight;

    @OneToOne(fetch = FetchType.LAZY)
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
        this.checkedBaggages = CheckedBaggagesFactory.createCheckedBaggages(this);
    }

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, boolean isVip, boolean isCheckedIn,
                     boolean isChild, Flight flight, CheckedBaggages checkedBaggages) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.isVip = isVip;
        this.isCheckedIn = isCheckedIn;
        this.isChild = isChild;
        this.flight = flight;
        this.checkedBaggages = checkedBaggages;
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
        float price = checkedBaggages.calculatePrice();
        return isVip ? price * VIP_DISCOUNT : price;
    }

    public void addBaggage(Baggage baggage) {
        checkedBaggages.addBaggage(baggage);
    }

    public List<Baggage> getBaggages() {
        return checkedBaggages.getBaggages();
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