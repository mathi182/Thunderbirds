package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.BaggagesCollection;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CheckedBaggages extends BaggagesCollection {
    private static final int BAGGAGE_COUNT_LIMIT = 3;
    protected static final String TYPE = "checked";

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "passenger_id")
    protected List<Baggage> collection = new ArrayList<>();

    public CheckedBaggages(Passenger passenger) {
        this.passenger = passenger;
    }

    protected CheckedBaggages() {
        //for hibernate
    }

    public List<Baggage> getBaggages() {
        return Collections.unmodifiableList(collection);
    }

    public float calculatePrice() {
        float price = 0;
        for (Baggage baggage : collection) {
            price += baggage.getPrice();
        }
        return price;
    }

    @Override
    public void addBaggage(Baggage baggage) {
        if (collection.size() >= getBaggageCountLimit()) {
            throw new BaggageAmountUnauthorizedException();
        }
        setBaggagePrice(baggage);
        collection.add(baggage);
    }

    private void setBaggagePrice(Baggage baggage) {
        float price = 0;
        if (collection.size() >= getFreeBaggageCount()) {
            price = baggage.getBasePrice();
        }
        baggage.setPrice(price);
    }

    private int getBaggageCountLimit() {
        return passenger.isVip() ? BAGGAGE_COUNT_LIMIT + 1 : BAGGAGE_COUNT_LIMIT;
    }

    protected abstract Mass getWeightLimit();

    protected abstract Length getDimensionLimit();

    protected abstract int getFreeBaggageCount();

    protected abstract void validate(Baggage baggage);

    public abstract float calculateTotalCost();

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }
}