package ca.ulaval.glo4002.thunderbird.boarding.util.units;


public class Mass {

    private static final double GRAMS_IN_A_POUND = 453.592;
    private static final double GRAMS_IN_A_KILOGRAM = 1000;

    private final double grams;

    private Mass(double grams) {
        this.grams = grams;
    }

    public double toPounds() {
        return grams / GRAMS_IN_A_POUND;
    }

    public double toGrams() {
        return grams;
    }

    public double toKilograms() {
        return grams / GRAMS_IN_A_KILOGRAM;
    }

    public static Mass fromPounds(double pounds) {
        return new Mass(pounds * GRAMS_IN_A_POUND);
    }

    public static Mass fromGrams(double grams) {
        return new Mass(grams);
    }

    public static Mass fromKilograms(double kilograms) {
        return new Mass(kilograms * GRAMS_IN_A_KILOGRAM);
    }
}