package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.CheckedBaggageCollection;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.EconomicBaggageCollection;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class CheckedBaggageCollectionTest {
    private static final double SECOND_BAGGAGE_PRICE = 3;
    private static final int BAGGAGE_COUNT_LIMIT = 3;
    private static final int BAGGAGE_VIP_COUNT_LIMIT = BAGGAGE_COUNT_LIMIT + 1;


    private final Passenger passenger = mock(Passenger.class);
    private final Baggage firstBaggage = mock(Baggage.class);
    private final Baggage secondBaggage = mock(Baggage.class);

    private final CheckedBaggageCollection checkedBaggageCollection = new EconomicBaggageCollection(passenger);

    @Before
    public void setUp() {
        willReturn(1d).given(firstBaggage).getPrice();
        willReturn(2d).given(secondBaggage).getPrice();

        willReturn(SECOND_BAGGAGE_PRICE).given(secondBaggage).getPrice();
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void whenGettingAllBaggages_shouldBeEmpty() {
        List<Baggage> baggages = checkedBaggageCollection.getBaggages();

        assertTrue(baggages.isEmpty());
    }

    @Test
    public void givenWeAddABaggage_whenRetrievingTheList_shouldNotBeEmpty() {
        checkedBaggageCollection.addBaggage(firstBaggage);

        List<Baggage> baggages = checkedBaggageCollection.getBaggages();

        assertFalse(baggages.isEmpty());
    }

    @Test
    public void givenMaximumNumberOfBaggages_whenAddingAnother_shouldThrowAnException() {
        exception.expect(BaggageAmountUnauthorizedException.class);

        for (int i = 0; i < BAGGAGE_COUNT_LIMIT + 1; i++) {
            checkedBaggageCollection.addBaggage(firstBaggage);
        }

        assertEquals(3, checkedBaggageCollection.getBaggages().size());
    }

    @Test
    public void givenMaximumNumberOfBaggagesForVip_whenAddingAnother_shouldThrowAnException() {
        willReturn(true).given(passenger).isVip();
        exception.expect(BaggageAmountUnauthorizedException.class);

        for (int i = 0; i < BAGGAGE_VIP_COUNT_LIMIT + 1; i++) {
            checkedBaggageCollection.addBaggage(firstBaggage);
        }

        assertEquals(3, checkedBaggageCollection.getBaggages().size());

    }
}