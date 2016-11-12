package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.Luggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.LuggageAmountAuthorizedException;
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
    private List<Luggage> luggages;

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate, String flightNumber, List<Luggage> luggages) {
        this(passengerHash, seatClass, flightDate, flightNumber);
        this.luggages = new ArrayList<>(luggages);

    }

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate, String flightNumber) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.luggages = new ArrayList<>();
    }

    protected Passenger() {
        //for hibernate
    }

    public UUID getHash() {
        return passengerHash;
    }

    public boolean isSameSeatClass(Seat.SeatClass seatClass) {
        return this.seatClass.equals(seatClass);
    }

    public void addBaggage(Luggage luggage) {
        if (getLuggagesCount() < BAGGAGE_AMOUNT_AUTHORIZED) {
            float price = getLuggageBasePrice();
            luggage.setPrice(price);
            luggages.add(luggage);
        } else {
            throw new LuggageAmountAuthorizedException();
        }
    }

    public int getLuggagesCount() {
        return luggages.size();
    }

    public float getLuggageBasePrice() {
        if (luggages.isEmpty()) {
            return FIRST_BAGGAGE_BASE_PRICE;
        } else {
            return ADDITIONAL_BAGGAGE_BASE_PRICE;
        }
    }

    public List<Luggage> getLuggages() {
        return luggages;
    }

    public Instant getFlightDate() {
        return flightDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
