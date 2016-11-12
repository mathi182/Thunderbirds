package ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.validationStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.Luggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.exceptions.LuggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.exceptions.LuggageWeightInvalidException;
import org.junit.Before;
import org.junit.Test;

public class EconomyCheckedLuggageValidationStrategyTest {
    public static final int INVALID_WEIGHT_IN_GRAMS = 23001;
    public static final int VALID_LINEAR_DIMENSION_IN_MM = 10;
    public static final int VALID_WEIGHT_IN_GRAMS = 10;
    public static final int INVALID_LINEAR_DIMENSION_IN_MM = 1581;
    public static final String VALID_TYPE = "checked";
    
    private EconomyCheckedLuggageValidationStrategy economyCheckedLuggageValidationStrategy;

    @Before
    public void setUp() {
        economyCheckedLuggageValidationStrategy = new EconomyCheckedLuggageValidationStrategy();
    }

    @Test
    public void givenValidCheckedLuggage_whenValidating_shouldDoNothing() {
        Luggage luggage = new Luggage(VALID_LINEAR_DIMENSION_IN_MM, VALID_WEIGHT_IN_GRAMS, VALID_TYPE);

        economyCheckedLuggageValidationStrategy.validateLuggage(luggage);
    }

    @Test(expected = LuggageWeightInvalidException.class)
    public void givenInvalidWeightInGCheckedLuggage_whenValidating_shouldThrowLuggageWeightInvalidException() {
        Luggage luggage = new Luggage(VALID_LINEAR_DIMENSION_IN_MM, INVALID_WEIGHT_IN_GRAMS, VALID_TYPE);

        economyCheckedLuggageValidationStrategy.validateLuggage(luggage);
    }

    @Test(expected = LuggageDimensionInvalidException.class)
    public void givenInvalidDimensionInMmCheckedLuggage_whenValidating_shouldThrowLuggageDimensionInvalidException() {
        Luggage luggage = new Luggage(INVALID_LINEAR_DIMENSION_IN_MM, VALID_WEIGHT_IN_GRAMS, VALID_TYPE);

        economyCheckedLuggageValidationStrategy.validateLuggage(luggage);
    }
}