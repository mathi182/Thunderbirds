package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class CheckedBaggages {
    public static final int CHECKED_BAGGAGE_COUNT_LIMIT = 3;
    public static final int CHECKED_BAGGAGE_COST = 50;

    @OneToMany(cascade = {CascadeType.ALL})
    protected List<Baggage> baggages;

    @Id
    private UUID passengerHash;

    public CheckedBaggages(UUID passengerHash) {
        this.passengerHash = passengerHash;
        this.baggages = new ArrayList<>();
    }

    protected CheckedBaggages() {
        //for hibernate
    }

    public float calculatePrice() {
        float price = 0;
        for (Baggage baggage : baggages) {
            price += baggage.getPrice();
        }
        return price;
    }

    public void addBaggage(Baggage baggage) {
        if (baggages.size() == CHECKED_BAGGAGE_COUNT_LIMIT) {
            throw new BaggageAmountUnauthorizedException();
        }
        validateBaggage(baggage);
        SetBaggagePrice(baggage);

        baggages.add(baggage);
    }

    public List<Baggage> getBaggages() {
        return Collections.unmodifiableList(baggages);
    }

    protected abstract void validateBaggage(Baggage baggage);

    protected abstract void SetBaggagePrice(Baggage baggage);
}