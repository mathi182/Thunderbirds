package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.BaggageAmountAuthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
        this(passengerHash, seatClass, flightDate, flightNumber);
        this.baggages = baggages;
        for (Baggage baggage : baggages) {
            baggage.setPassenger(this);
        }
    }

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, Instant flightDate, String flightNumber) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.flightNumber = flightNumber;
        this.flightDate = flightDate;
        this.baggages = new ArrayList<>();
    }

    public UUID getHash() {
        return passengerHash;
    }

    public boolean isSameSeatClass(Seat.SeatClass seatClass){
        return this.seatClass.equals(seatClass);
    }

    public void addBaggage(Baggage baggage) {
        if (getBaggagesCount() < BAGGAGE_AMOUNT_AUTHORIZED) {
            float price = getBaggageBasePrice();
            baggage.setPrice(price);
            this.baggages.add(baggage);
            baggage.setPassenger(this);
        } else {
            throw new BaggageAmountAuthorizedException();
        }
    }

    public int getBaggagesCount() {
        return this.baggages.size();
    }

    public float getBaggageBasePrice() {
        if (this.baggages.isEmpty()) {
            return FIRST_BAGGAGE_BASE_PRICE;
        } else {
            return ADDITIONAL_BAGGAGE_BASE_PRICE;
        }
    }

    public List<Baggage> getBaggages() {
        return this.baggages;
    }

    public Instant getFlightDate() {
        return this.flightDate;
    }

    public String getFlightNumber() {
        return this.flightNumber;
    }
}
