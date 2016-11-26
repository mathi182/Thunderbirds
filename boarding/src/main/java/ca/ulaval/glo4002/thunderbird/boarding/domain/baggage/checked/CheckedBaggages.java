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
public class CheckedBaggages {
    public static final int CHECKED_BAGGAGE_COUNT_LIMIT = 3;
    public static final int CHECKED_BAGGAGE_COST = 50;

    private int freeCheckedBaggageCount;
    private int weightLimitInGrams;
    private int dimensionLimitInMm;
    private int checkedBaggageCountLimit;
    private int checkedBaggageCost;

    @OneToMany(cascade = {CascadeType.ALL})
    protected List<Baggage> baggages;

    @Id
    private UUID passengerHash;

    public CheckedBaggages(UUID passengerHash,
                           int freeCheckedBaggageCount,
                           int weightLimitInGrams,
                           int dimensionLimitInMm) {
        this.freeCheckedBaggageCount = freeCheckedBaggageCount;
        this.weightLimitInGrams = weightLimitInGrams;
        this.dimensionLimitInMm = dimensionLimitInMm;
        this.checkedBaggageCountLimit = CHECKED_BAGGAGE_COUNT_LIMIT;
        this.checkedBaggageCost = CHECKED_BAGGAGE_COST;
        this.baggages = new ArrayList<>();
        this.passengerHash = passengerHash;
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
        if (baggages.size() == checkedBaggageCountLimit) {
            throw new BaggageAmountUnauthorizedException();
        }
        validateBaggage(baggage);
        setBaggagePrice(baggage);

        baggages.add(baggage);
    }

    public List<Baggage> getBaggages() {
        return Collections.unmodifiableList(baggages);
    }

    public void validateBaggage(Baggage baggage) {
        baggage.validate(dimensionLimitInMm, weightLimitInGrams);
    }

    public void setBaggagePrice(Baggage baggage) {
        if (baggages.size() < freeCheckedBaggageCount) {
            baggage.setPrice(0);
        } else {
            baggage.setPrice(checkedBaggageCost);
        }
    }
}