package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.PersonalBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageFormatUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class PersonalBaggageCollectionTest {

    private static final float DELTA = 0.01f;
    private static final String TYPE = "personal";
    private static final List<Baggage> EMPTY_LIST = new ArrayList<>();
    private static final double BAGGAGE_TOTAL_COST = 0;

    private PersonalBaggageCollection baggageCollection;
    private Baggage baggage;

    @Before
    public void setup() {
        Passenger passenger = mock(Passenger.class);
        baggage = mock(PersonalBaggage.class);
        baggageCollection = new PersonalBaggageCollection(passenger);
    }

    @Test
    public void shouldReturnRightValue() {
        assertEquals(TYPE, baggageCollection.getCollectionType());
        assertEquals(EMPTY_LIST, baggageCollection.getBaggages());
    }

    @Test
    public void whenCalculatingTotalCost_shouldReturnFree() {
        double cost = baggageCollection.calculateTotalCost();

        assertEquals(BAGGAGE_TOTAL_COST, cost, DELTA);
    }

    @Test
    public void givenABaggage_whenAddingABaggageToTheCollection_shouldCollectionContainIt() {
        baggageCollection.addBaggage(baggage);

        boolean containBaggage = baggageCollection.collection.contains(baggage);

        assertTrue(containBaggage);
    }

    @Test(expected = BaggageAmountUnauthorizedException.class)
    public void givenACollectionWithAlreadyABaggage_whenValidating_shouldThrowException() {
        baggageCollection.addBaggage(baggage);

        baggageCollection.validateCollection(baggage);
    }

    @Test(expected = BaggageFormatUnauthorizedException.class)
    public void givenABaggageWithASpeciality_whenValidating_shouldThrowException() {
        willReturn(true).given(baggage).hasSpeciality(any());

        baggageCollection.validateCollection(baggage);
    }

    @Test
    public void givenAnEmptyCollectionAndAValidBaggage_whenValidatingBaggage_shouldNotThrowException() {
        baggageCollection.validateCollection(baggage);
    }
}
