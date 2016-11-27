package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.unitConverters;

import ca.ulaval.glo4002.thunderbird.boarding.rest.exceptions.IllegalFieldWebException;

public class DimensionConverterFactory {
    public static final String INCH_UNIT_FROM_REQUEST = "po";
    public static final String CENTIMER_UNIT_FROM_REQUEST = "cm";

    public DimensionConverter getConverter(String linearDimensionUnitsString) {
        switch (linearDimensionUnitsString) {
            case INCH_UNIT_FROM_REQUEST:
                return new DimensionConverterInches();
            case CENTIMER_UNIT_FROM_REQUEST:
                return new DimensionConverterCentimeters();
            default:
                throw new IllegalFieldWebException();
        }
    }
}