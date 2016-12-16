package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.PersonalBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageAmountUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.exceptions.BaggageFormatUnauthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Oversize;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Speciality;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonalBaggageCollectionTest {

    private static final float DELTA = 0.01f;
    private static final String TYPE = "personal";
    private static final List<Baggage> EMPTY_LIST = new ArrayList<>();
    private static final Length LINEAR_DIMENSION = Length.fromCentimeters(50);
    private static final Mass WEIGHT = Mass.fromKilograms(5);
    private static final float BAGGAGE_TOTAL_COAST = 0;
    private Speciality oversizeSpeciality = new Oversize();

    private PersonalBaggageCollection baggageCollection;
    private Baggage baggage;

    @Before
    public void setup() {
        baggage = new PersonalBaggage(LINEAR_DIMENSION, WEIGHT, TYPE);
        baggageCollection = new PersonalBaggageCollection();
    }

    @Test
    public void shouldReturnRightValue() {
        assertEquals(TYPE, baggageCollection.getCollectionType());
        assertEquals(EMPTY_LIST, baggageCollection.getBaggages());
        assertEquals(BAGGAGE_TOTAL_COAST, baggageCollection.calculateTotalCost(), DELTA);
    }

    @Test(expected = BaggageAmountUnauthorizedException.class)
    public void givenACollectionWithAlreadyABaggage_whenValidating_shouldThrowException() {
        baggageCollection.addBaggage(baggage);

        baggageCollection.validate(baggage);
    }

    @Test(expected = BaggageFormatUnauthorizedException.class)
    public void givenABaggageWithASpeciality_whenValidating_shouldThrowException() {
        baggage.addSpeciality(oversizeSpeciality);

        baggageCollection.validate(baggage);
    }

    @Test
    public void givenAnEmptyCollectionAndAValidBaggage_whenValidatingBaggage_shouldNotThrowException() {
        baggageCollection.validate(baggage);
    }
}
