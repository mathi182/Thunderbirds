package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.BaggageAmountAuthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Passenger {
    public static final int BAGGAGE_AMOUNT_AUTHORIZED = 3;
    public static final float FIRST_BAGGAGE_BASE_PRICE = 0f;
    public static final float ADDITIONAL_BAGGAGE_BASE_PRICE = 50f;

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID passengerHash;
    private Seat.SeatClass seatClass;
    private Instant flightDate;
    private String flightNumber;
    private boolean isVip;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Baggage> baggages;

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate, String flightNumber, boolean isVip) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.isVip = isVip;
        this.baggages = new ArrayList<>();
    }

    protected Passenger() {
        //for hibernate
    }

    public UUID getHash() {
        return passengerHash;
    }

    public boolean isSameSeatClass(Seat.SeatClass otherSeatClass) {
        return seatClass.equals(otherSeatClass);
    }

    public void addBaggage(Baggage baggage) {
        if (getBaggageCount() < BAGGAGE_AMOUNT_AUTHORIZED) {
            float price = getBaggageBasePrice();
            baggage.setPrice(price);
            baggages.add(baggage);
        } else {
            throw new BaggageAmountAuthorizedException();
        }
    }

    public int getBaggageCount() {
        return baggages.size();
    }

    public float getBaggageBasePrice() {
        if (baggages.isEmpty()) {
            return FIRST_BAGGAGE_BASE_PRICE;
        } else {
            return ADDITIONAL_BAGGAGE_BASE_PRICE;
        }
    }

    public float getBaggagesTotalPrice() {
        float totalPrice = 0f;
        for (Baggage baggage : baggages) {
            totalPrice += baggage.getPrice();
        }

        return totalPrice;
    }

    public Seat.SeatClass getSeatClass() {
        return seatClass;
    }

    public List<Baggage> getBaggages() {
        return baggages;
    }

    public boolean isVip() {
        return isVip;
    }

    public Instant getFlightDate() {
        return flightDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}