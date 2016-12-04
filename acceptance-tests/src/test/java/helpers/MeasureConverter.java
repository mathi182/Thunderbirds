package helpers;

import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MeasureConverter {
    private static final String KG_AS_STRING = "kg";
    private static final String CM_AS_STRING = "cm";

    public static Length getLengthFromString(String lengthAsString) {
        String[] lengthValueUnitTable = lengthAsString.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        String unit = lengthValueUnitTable[1];
        double value = Double.valueOf(lengthValueUnitTable[0]);
        switch (unit) {
            case CM_AS_STRING:
                return Length.fromCentimeters(value);
            default:
                throw new NotImplementedException();
        }
    }

    public static Mass getMassFromString(String massAsString) {
        String[] lengthValueUnitTable = massAsString.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        String unit = lengthValueUnitTable[1];
        double value = Double.valueOf(lengthValueUnitTable[0]);
        switch (unit) {
            case KG_AS_STRING:
                return Mass.fromKilograms(value);
            default:
                throw new NotImplementedException();
        }
    }
}
