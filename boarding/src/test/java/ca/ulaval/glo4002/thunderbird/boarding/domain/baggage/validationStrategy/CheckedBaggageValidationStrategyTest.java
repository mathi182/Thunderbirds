package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.seatClassBaggageStrategy.BaggageValidationDTO;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CheckedBaggageValidationStrategyTest {

    public static final int LIMIT1 = 1;
    public static final int COUNT_LOWER_THEN_LIMIT = 0;
    public static final int WEIGHT_LIMIT = 10;
    public static final int DIMENSION_LIMIT = 10;
    private Baggage baggageMock;

    @Before
    public void setUp() throws Exception {
        baggageMock = mock(Baggage.class);
    }

    @Test(expected = BaggageAmountUnauthorizedException.class)
    public void givenCountToLimit_whenValidating_shouldThrowBaggageAmountUnauthorized() {
        BaggageValidationDTO baggageValidationDTO = new BaggageValidationDTO();
        baggageValidationDTO.checkedBaggageCountLimit = LIMIT1;
        baggageValidationDTO.numberOfBaggagesOfSameType = LIMIT1;

        CheckedBaggageValidationStrategy strategy = new CheckedBaggageValidationStrategy();
        strategy.validate(mock(Baggage.class), baggageValidationDTO);
    }


    @Test
    public void givenValidLimit_whenValidating_shouldCallValidateBaggage() throws Exception {
        BaggageValidationDTO baggageValidationDTO = new BaggageValidationDTO();
        baggageValidationDTO.checkedBaggageCountLimit = LIMIT1;
        baggageValidationDTO.numberOfBaggagesOfSameType = COUNT_LOWER_THEN_LIMIT;
        baggageValidationDTO.dimensionLimitInMm = DIMENSION_LIMIT;
        baggageValidationDTO.weightLimitInGrams = WEIGHT_LIMIT;

        CheckedBaggageValidationStrategy strategy = new CheckedBaggageValidationStrategy();
        strategy.validate(baggageMock, baggageValidationDTO);

        verify(baggageMock).validate(DIMENSION_LIMIT, WEIGHT_LIMIT);
    }
}