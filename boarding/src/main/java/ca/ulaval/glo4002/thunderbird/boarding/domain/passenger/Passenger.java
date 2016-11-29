package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggages;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggagesFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
public class Passenger {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID passengerHash;
    private Seat.SeatClass seatClass;
    private Instant flightDate;
    private String flightNumber;
    private boolean isVip;
    private boolean isCheckin;

    @Embedded
    private CheckedBaggages checkedBaggages;

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate, String flightNumber, boolean isVip, boolean isCheckin) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.isVip = isVip;
        this.isCheckin = isCheckin;
        this.checkedBaggages = CheckedBaggagesFactory.getCheckedBaggages(seatClass);
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
        return checkedBaggages.calculatePrice();
    }

    public void addBaggage(Baggage baggage) {
        checkedBaggages.addBaggage(baggage);
    }

    public List<Baggage> getBaggages() {
        return checkedBaggages.getBaggages();
    }

    public boolean isCheckin() {
        return isCheckin;
    }
}