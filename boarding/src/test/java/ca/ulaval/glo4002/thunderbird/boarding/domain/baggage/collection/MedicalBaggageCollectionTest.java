package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.MedicalBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageFormatUnauthorizedException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class MedicalBaggageCollectionTest {

    private static final float DELTA = 0.01f;
    private static final String TYPE = "medical";
    private static final List<Baggage> EMPTY_LIST = new ArrayList<>();
    private static final float BAGGAGE_TOTAL_COAST = 0;

    private MedicalBaggageCollection baggageCollection;
    private Baggage baggage;

    @Before
    public void setup() {
        baggage = mock(MedicalBaggage.class);
        baggageCollection = new MedicalBaggageCollection();
    }

    @Test
    public void shouldReturnRightValue() {
        assertEquals(TYPE, baggageCollection.getCollectionType());
        assertEquals(EMPTY_LIST, baggageCollection.getBaggages());
        assertEquals(BAGGAGE_TOTAL_COAST, baggageCollection.calculateTotalCost(), DELTA);
    }

    @Test
    public void givenABaggage_whenAddingABaggageToTheCollection_shouldCollectionContainIt() {
        baggageCollection.addBaggage(baggage);

        boolean containBaggage = baggageCollection.collection.contains(baggage);

        assertTrue(containBaggage);
    }

    @Test(expected = BaggageFormatUnauthorizedException.class)
    public void givenABaggageWithASpeciality_whenValidating_shouldThrowException() {
        willReturn(true).given(baggage).hasSpecialities();

        baggageCollection.validate(baggage);
    }

    @Test
    public void givenAnEmptyCollectionAndAValidBaggage_whenValidatingBaggage_shouldNotThrowException() {
        baggageCollection.validate(baggage);
    }
}
