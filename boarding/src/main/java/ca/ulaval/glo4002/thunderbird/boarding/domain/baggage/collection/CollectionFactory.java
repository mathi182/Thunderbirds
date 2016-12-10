package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.BusinessCheckedBaggages;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.EconomicCheckedBaggages;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;

public class CollectionFactory {
    private static final String STANDARD = "standard";
    private static final String PERSONAL = "personal";
    private static final String MEDICAL = "medical";
    private static final String CHECKED = "checked";

    public BaggagesCollection createCollection(Passenger passenger, String type) {

        switch (type) {
            case STANDARD:
                return new StandardCollection();
            case PERSONAL:
                return new PersonalCollection();
            case MEDICAL:
                return new MedicalCollection();
            case CHECKED:
                switch (passenger.getSeatClass()) {
                    case BUSINESS:
                        return new BusinessCheckedBaggages(passenger);
                    case ECONOMY:
                        return new EconomicCheckedBaggages(passenger);
                    default:
                        throw new NoSuchStrategyException("Unknown seatClass " + passenger.getSeatClass());
                }
            default:
                throw new NoSuchStrategyException("Unknown baggage type " + type);
        }
    }
}
