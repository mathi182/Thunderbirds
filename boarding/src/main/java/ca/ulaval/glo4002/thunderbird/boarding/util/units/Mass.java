package ca.ulaval.glo4002.thunderbird.boarding.util.units;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.Embeddable;

@Embeddable
public class Mass {
    private static final double GRAMS_IN_A_POUND = 453.592;
    private static final double GRAMS_IN_A_KILOGRAM = 1000;

    private double grams;

    private Mass(double grams) {
        this.grams = grams;
    }

    protected Mass() {
        // for hibernate
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

    public static Mass fromString(String massAsString) {
        String[] lengthValueUnitTable = massAsString.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        String unit = lengthValueUnitTable[1];
        double value = Double.valueOf(lengthValueUnitTable[0]);
        switch (unit) {
            case "kg" :
                return Mass.fromKilograms(value);
            default:
                throw new NotImplementedException();
        }
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Mass)) {
            return false;
        }

        Mass other = (Mass) obj;
        return grams == other.grams;
    }

    public boolean isSuperiorTo(Mass mass) {
        return grams > mass.grams;
    }
}