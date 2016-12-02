package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class CheckedBaggages {
    private static final int BAGGAGE_COST = 50;
    private static final int BAGGAGE_COUNT_LIMIT = 3;

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID checkedBaggageId;

    @OneToOne(mappedBy = "checkedBaggages", fetch = FetchType.EAGER)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "passenger_id")
    private List<Baggage> baggages = new ArrayList<>();

    public CheckedBaggages(Passenger passenger) {
        this.checkedBaggageId = passenger.getHash();
        this.passenger = passenger;
    }

    protected CheckedBaggages() {
        //for hibernate
    }

    public List<Baggage> getBaggages() {
        return Collections.unmodifiableList(baggages);
    }

    public float calculatePrice() {
        float price = 0;
        for (Baggage baggage : baggages) {
            price += baggage.getPrice();
        }
        return price;
    }

    public void addBaggage(Baggage baggage) {
        if (baggages.size() >= getBaggageCountLimit()) {
            throw new BaggageAmountUnauthorizedException();
        }
        validateBaggage(baggage);
        setBaggagePrice(baggage);

        baggages.add(baggage);
    }

    private void validateBaggage(Baggage baggage) {
        baggage.validate(getDimensionLimit(), getWeightLimit());
    }

    private void setBaggagePrice(Baggage baggage) {
        if (baggages.size() < getFreeBaggageCount()) {
            baggage.setPrice(0);
        } else {
            baggage.setPrice(getBaggageCost());
        }
    }

    private float getBaggageCost() {
        //TODO: Add penalty if overweight
        return BAGGAGE_COST;
    }

    private int getBaggageCountLimit() {
        return passenger.isVip() ? BAGGAGE_COUNT_LIMIT + 1 : BAGGAGE_COUNT_LIMIT;
    }

    protected abstract Mass getWeightLimit();

    protected abstract Length getDimensionLimit();

    protected abstract int getFreeBaggageCount();

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }
}