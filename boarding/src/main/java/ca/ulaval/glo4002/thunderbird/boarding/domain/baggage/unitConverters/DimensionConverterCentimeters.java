package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.unitConverters;

public class DimensionConverterCentimeters implements DimensionConverter {

    public static final int MILLIMETERS_IN_CM = 10;

    @Override
    public int convertToMillimeters(double dimension) {
        return (int) (dimension * MILLIMETERS_IN_CM);
    }
}
