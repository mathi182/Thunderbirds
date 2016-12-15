package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.StandardBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageFormatUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StandardBaggageCollectionTest {

    private static final String TYPE = "standard";
    private static final List<Baggage> EMPTY_LIST = new ArrayList<>();
    private static final Length LINEAR_DIMENSION = Length.fromCentimeters(50);
    private static final Mass WEIGHT = Mass.fromKilograms(5);


    private StandardBaggageCollection baggageCollection;
    private Baggage baggage;

    @Before
    public void setup() {
        baggage = new StandardBaggage(LINEAR_DIMENSION, WEIGHT, TYPE);
        baggageCollection = new StandardBaggageCollection();
    }

    @Test
    public void shouldReturnRightValue() {
        assertEquals(TYPE, baggageCollection.getCollectionType() );
        assertEquals(EMPTY_LIST, baggageCollection.getBaggages());
    }

    @Ignore
    @Test (expected = BaggageAmountUnauthorizedException.class)
    public void givenACollectionWithAlreadyABaggage_whenValidating_shouldThrowException() {
        baggageCollection.addBaggage(baggage);

        baggageCollection.validate(baggage);

    }
}
