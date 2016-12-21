package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.MedicalBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Speciality;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class PassengerBaggageCollectionsTest {

    private static final UUID PASSENGER_UUID = UUID.randomUUID();
    private PassengerBaggageCollections collections;
    private Baggage baggage;


    @Before
    public void setup() {
        Passenger passenger = mock(Passenger.class);
        collections = new PassengerBaggageCollections(passenger);
        baggage = mock(MedicalBaggage.class);
        willReturn(false).given(baggage).hasSpeciality(any(Speciality.class));
    }

    @Test
    public void givenABaggageCollections_whenAddingANewTypeOfBaggage_shouldAddANewCollection() {
        willReturn("medical").given(baggage).getType();
        int collectionSizeBefore = collections.collection.size();

        collections.addBaggage(baggage);

        int expectedSize = collectionSizeBefore + 1;
        int actualSize = collections.collection.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void givenABaggageCollections_whenAddingASecondBaggageOfAType_shouldNotAddANewCollection() {
        willReturn("medical").given(baggage).getType();
        collections.addBaggage(baggage);
        int expectedSize = collections.collection.size();

        collections.addBaggage(baggage);

        int actualSize = collections.collection.size();
        assertEquals(expectedSize, actualSize);
    }
}
