package ca.ulaval.glo4002.thunderbird.boarding.util.units;

public class Length {

    private static final double MILLIMETERS_IN_A_CENTIMETER = 10;
    private static final double MILLIMETERS_IN_A_INCH = 25.4;

    private final double millimeters;

    private Length(double millimeters) {
        this.millimeters = millimeters;
    }

    public double toCentimeters() {
        return millimeters / MILLIMETERS_IN_A_CENTIMETER;
    }

    public double toInches() {
        return millimeters / MILLIMETERS_IN_A_INCH;
    }

    public double toMillimeters() {
        return millimeters;
    }

    public static Length fromMillimeters(double millimeters) {
        return new Length(millimeters);
    }

    public static Length fromCentimeters(double centimeters) {
        return new Length(centimeters * MILLIMETERS_IN_A_CENTIMETER);
    }

    public static Length fromInches(double inches) {
        return new Length(inches * MILLIMETERS_IN_A_INCH);
    }

}