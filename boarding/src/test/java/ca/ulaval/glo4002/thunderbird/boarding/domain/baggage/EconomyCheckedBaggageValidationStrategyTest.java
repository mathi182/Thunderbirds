package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.EconomyCheckedBaggageValidationStrategy;
import org.junit.Before;
import org.junit.Test;

public class EconomyCheckedBaggageValidationStrategyTest {

    public static final int INVALID_WEIGHT_IN_GRAMS = 23001;
    public static final int VALID_LINEAR_DIMENSION_IN_MM = 10;
    public static final int VALID_WEIGHT_IN_GRAMS = 10;
    public static final int INVALID_LINEAR_DIMENSION_IN_MM = 1581;
    public static final String VALID_TYPE = "checked";
    private EconomyCheckedBaggageValidationStrategy economyCheckedBaggageValidationStrategy;

    @Before
    public void setUp() {
        economyCheckedBaggageValidationStrategy = new EconomyCheckedBaggageValidationStrategy();
    }

    @Test
    public void givenValidCheckedBaggage_whenValidate_shouldDoNothing() {
        Baggage checkedBaggage = new Baggage(VALID_LINEAR_DIMENSION_IN_MM, VALID_WEIGHT_IN_GRAMS, VALID_TYPE);
        economyCheckedBaggageValidationStrategy.validateBaggage(checkedBaggage);
    }

    @Test(expected = BaggageWeightInvalidException.class)
    public void givenInvalidWeightInGCheckedBaggage_whenValidate_shouldThrowBaggageWeightInvalidException() {
        Baggage checkedBaggage = new Baggage(VALID_LINEAR_DIMENSION_IN_MM, INVALID_WEIGHT_IN_GRAMS, VALID_TYPE);
        economyCheckedBaggageValidationStrategy.validateBaggage(checkedBaggage);
    }

    @Test(expected = BaggageDimensionInvalidException.class)
    public void givenInvalidDimensionInMmCheckedBaggage_whenValidate_shouldThrowBaggageDimensionInvalidException() {
        Baggage checkedBaggage = new Baggage(INVALID_LINEAR_DIMENSION_IN_MM, VALID_WEIGHT_IN_GRAMS, VALID_TYPE);
        economyCheckedBaggageValidationStrategy.validateBaggage(checkedBaggage);
    }
}