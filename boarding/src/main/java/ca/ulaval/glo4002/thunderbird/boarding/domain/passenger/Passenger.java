package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.BaggageAmountAuthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
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

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "passenger")
    @JsonManagedReference
    private List<Baggage> baggages;

    public Passenger() {
        //for hibernate
    }

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass, List<Baggage> baggages) {
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.baggages = baggages;
        for (Baggage baggage : baggages) {
            baggage.setPassenger(this);
        }
    }

    public Passenger(UUID passengerHash, Seat.SeatClass seatClass){
        this.passengerHash = passengerHash;
        this.seatClass = seatClass;
        this.baggages = new ArrayList<Baggage>();
    }

    public UUID getHash() {
        return passengerHash;
    }

    public boolean isSameSeatClass(Seat.SeatClass seatClass){
        return this.seatClass.equals(seatClass);
    }

    public void addBaggage(Baggage baggage) {
        if (getBaggagesCount() < BAGGAGE_AMOUNT_AUTHORIZED) {
            baggage = setBaggagePrice(baggage);
            this.baggages.add(baggage);
            baggage.setPassenger(this);
        } else {
            throw new BaggageAmountAuthorizedException();
        }
    }

    public int getBaggagesCount() {
        return this.baggages.size();
    }

    //TODO: ecrire le test pour verifier que le set se fait vraiment correctement...
    private Baggage setBaggagePrice(Baggage baggage) {
        float baggagePrice;

        if (this.baggages.isEmpty()) {
            baggagePrice = FIRST_BAGGAGE_BASE_PRICE;
        } else {
            baggagePrice = ADDITIONAL_BAGGAGE_BASE_PRICE;
        }
        baggage.setPrice(baggagePrice);

        return baggage;
    }
}
