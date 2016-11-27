package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class CheckedBaggages {
    private static final float FREE = 0;

    private int freeCheckedBaggageCount;
    private int weightLimitInGrams;
    private int dimensionLimitInMm;
    private int checkedBaggageCountLimit;
    private int checkedBaggageCost;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Baggage> baggages;

    public CheckedBaggages(int freeCheckedBaggageCount,
                           int checkedBaggageCost,
                           int checkedBaggageCountLimit,
                           int weightLimitInGrams,
                           int dimensionLimitInMm) {
        this.baggages = new ArrayList<>();
        this.freeCheckedBaggageCount = freeCheckedBaggageCount;
        this.checkedBaggageCost = checkedBaggageCost;
        this.checkedBaggageCountLimit = checkedBaggageCountLimit;
        this.weightLimitInGrams = weightLimitInGrams;
        this.dimensionLimitInMm = dimensionLimitInMm;
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
        if (baggages.size() >= checkedBaggageCountLimit) {
            throw new BaggageAmountUnauthorizedException();
        }
        validateBaggage(baggage);
        setBaggagePrice(baggage);

        baggages.add(baggage);
    }

    private void validateBaggage(Baggage baggage) {
        baggage.validate(dimensionLimitInMm, weightLimitInGrams);
    }

    private void setBaggagePrice(Baggage baggage) {
        if (baggages.size() < freeCheckedBaggageCount) {
            baggage.setPrice(FREE);
        } else {
            baggage.setPrice(checkedBaggageCost);
        }
    }
}