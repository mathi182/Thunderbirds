package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.unitConverters;

import ca.ulaval.glo4002.thunderbird.boarding.rest.exceptions.IllegalFieldWebException;

public class WeightConverterFactory {
    public static final String POUND_FROM_REQUEST = "lbs";
    public static final String KILOGRAMS_FROM_REQUEST = "kg";

    public WeightConverter getConverter(String weightUnitsFromRequest) {
        switch (weightUnitsFromRequest) {
            case KILOGRAMS_FROM_REQUEST:
                return new WeightConverterKilograms();
            case POUND_FROM_REQUEST:
                return new WeightConverterPound();
            default:
                throw new IllegalFieldWebException();
        }
    }
}