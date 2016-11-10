package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters;

import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;

public class WeightConverterFactory {
    public static final String POUND_FROM_REQUEST = "lbs";
    public static final String KILOGRAMS_FROM_REQUEST = "kg";

    public WeightConverter getConverter (String weightUnitsFromRequest) {
        switch (weightUnitsFromRequest) {
            case KILOGRAMS_FROM_REQUEST:
                return new WeightConverterKilograms();
            case POUND_FROM_REQUEST:
                return new WeightConverterPound();
            default:
                throw new NoSuchStrategyException("unknown");
        }
    }
}
