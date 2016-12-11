package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageDimensionInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageWeightInvalidException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class BaggageTest {
    private final UUID BAGGAGE_HASH = new UUID(2L, 2L);

    private final int DIMENSION_VALUE = 11;
    private final Length LINEAR_DIMENSION = Length.fromMillimeters(DIMENSION_VALUE);
    private final Length INVALID_DIMENSION = Length.fromMillimeters(DIMENSION_VALUE - 1);

    private final int WEIGHT_VALUE = 22;
    private final Mass WEIGHT = Mass.fromGrams(WEIGHT_VALUE);
    private final Mass INVALID_WEIGHT = Mass.fromGrams(WEIGHT_VALUE - 1);
    private final String CHECKED = "checked";
    private final Seat.SeatClass BUSINESS = Seat.SeatClass.BUSINESS;

    private BaggageFactory baggageFactory = new BaggageFactory();
    private Baggage baggage;

    @Before
    public void setup() {
        Passenger passenger = mock(Passenger.class);
        willReturn(BUSINESS).given(passenger).getSeatClass();
        NormalizedBaggageDTO baggageDTO = new NormalizedBaggageDTO(LINEAR_DIMENSION, WEIGHT, CHECKED);
        baggage = baggageFactory.createBaggage(passenger, baggageDTO);
    }

    @Test
    public void shouldReturnRightValues() {
        assertEquals(LINEAR_DIMENSION, baggage.getDimension());
        assertEquals(WEIGHT, baggage.getWeight());
        assertEquals(0, baggage.getPrice(), 0.0f);
    }

    @Test
    public void givenAPrice_whenSettingThisPrice_shouldBeSaved() {
        float expectedPrice = 5342;

        baggage.setPrice(expectedPrice);

        float actualPrice = baggage.getPrice();
        assertEquals(expectedPrice, actualPrice, 0.0f);
    }

    @Test
    public void whenDimensionAndWeightAreValid_shouldNotThrowAnException() {
        baggage.validate(LINEAR_DIMENSION, WEIGHT);
    }

    @Test(expected = BaggageDimensionInvalidException.class)
    public void whenDimensionIsInvalid_shouldThrowAnException() {
        baggage.validate(INVALID_DIMENSION, WEIGHT);
    }

    @Test(expected = BaggageWeightInvalidException.class)
    public void whenWeightIsInvalid_shouldThrowAnException() {
        baggage.validate(LINEAR_DIMENSION, INVALID_WEIGHT);
    }
}