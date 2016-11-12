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

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Baggage> baggages;

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate, String flightNumber, List<Baggage> baggages) {
        this(passengerHash, seatClass, flightDate, flightNumber);
        this.baggages = new ArrayList<>(baggages);

    }

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate, String flightNumber) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.baggages = new ArrayList<>();
    }

    protected Passenger() {
        //for hibernate
    }

    public UUID getHash() {
        return passengerHash;
    }

    public boolean isSameSeatClass(Seat.SeatClass seatClass) {
        return seatClass.equals(seatClass);
    }

    public void addBaggage(Baggage baggage) {
        if (getBaggageCount() < BAGGAGE_AMOUNT_AUTHORIZED) {
            Baggage pricedBaggage = createPricedBaggage(baggage);
            baggages.add(pricedBaggage);
        } else {
            throw new BaggageAmountAuthorizedException();
        }
    }

    public int getBaggageCount() {
        return baggages.size();
    }

    private Baggage createPricedBaggage(Baggage baggage) {
        float price = getBaggageBasePrice();
        return new Baggage(baggage.getBaggageHash(), baggage.getDimensionInMm(), baggage.getWeightInGrams(), baggage.getType(), price);
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

    public List<Baggage> getBaggages() {
        return baggages;
    }

    public Instant getFlightDate() {
        return flightDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
