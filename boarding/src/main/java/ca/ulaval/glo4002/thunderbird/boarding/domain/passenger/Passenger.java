package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggages;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggagesFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
public class Passenger {
    public static final float VIP_DISCOUNT = 0.95f;

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID passengerHash;
    private Seat.SeatClass seatClass;
    private Instant flightDate;
    private String flightNumber;
    private boolean isVip;
    private boolean isCheckedIn;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CheckedBaggages checkedBaggages;

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate,
                     String flightNumber, boolean isVip, boolean isCheckedIn) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.isVip = isVip;
        this.isCheckedIn = isCheckedIn;
        this.checkedBaggages = CheckedBaggagesFactory.getCheckedBaggages(this);
    }

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate, String flightNumber,
                     boolean isVip, boolean isCheckedIn, CheckedBaggages checkedBaggages) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.isVip = isVip;
        this.isCheckedIn = isCheckedIn;
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

    public Instant getFlightDate() {
        return flightDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public boolean isVip() {
        return isVip;
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

    public void checkin() {
        isCheckedIn = true;
    }
}