package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.unitConverters;

public class DimensionConverterInches implements DimensionConverter {
    public static final double MILLIMETERS_IN_INCH = 25.4;

    @Override
    public int convertToMillimeters(double dimension) {
        return (int) (dimension * MILLIMETERS_IN_INCH);
    }
}