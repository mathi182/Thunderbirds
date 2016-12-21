package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.MedicalBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.PersonalBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.BusinessBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Speciality;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class PassengerBaggageCollectionsTest {

    private static final UUID PASSENGER_UUID = UUID.randomUUID();
    public static final int PERSO_WEIGHT = 8;
    public static final int BUSINESS_WEIGHT = 45;
    public static final int MEDICAL_WEIGHT = 3;
    private PassengerBaggageCollections collections;
    private Baggage baggage;
    private Baggage personalBaggage;
    private Baggage businessBaggage;
    private Mass expectedMass = Mass.fromKilograms(MEDICAL_WEIGHT + BUSINESS_WEIGHT + PERSO_WEIGHT);


    @Before
    public void setup() {
        Passenger passenger = mock(Passenger.class);
        willReturn(Seat.SeatClass.BUSINESS).given(passenger).getSeatClass();
        collections = new PassengerBaggageCollections(passenger);
        baggage = mock(MedicalBaggage.class);
        personalBaggage = mock(PersonalBaggage.class);
        businessBaggage = mock(BusinessBaggage.class);
        willReturn(Mass.fromKilograms(PERSO_WEIGHT)).given(personalBaggage).getWeight();
        willReturn(Mass.fromKilograms(BUSINESS_WEIGHT)).given(businessBaggage).getWeight();
        willReturn(Mass.fromKilograms(MEDICAL_WEIGHT)).given(baggage).getWeight();
        willReturn(false).given(baggage).hasSpeciality(any(Speciality.class));
        willReturn("medical").given(baggage).getType();
        willReturn("checked").given(businessBaggage).getType();
        willReturn("personal").given(personalBaggage).getType();
    }

    @Test
    public void givenABaggageCollections_whenAddingANewTypeOfBaggage_shouldAddANewCollection() {
        int collectionSizeBefore = collections.collection.size();

        collections.addBaggage(baggage);

        int expectedSize = collectionSizeBefore + 1;
        int actualSize = collections.collection.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void givenABaggageCollections_whenAddingASecondBaggageOfAType_shouldNotAddANewCollection() {
        collections.addBaggage(baggage);
        int expectedSize = collections.collection.size();

        collections.addBaggage(baggage);

        int actualSize = collections.collection.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void givenACollectionsWithThreeBaggages_whenCalculatingMass_shouldReturnCorrectMass() {
        collections.addBaggage(baggage);
        collections.addBaggage(personalBaggage);
        collections.addBaggage(businessBaggage);

        Mass actualMass = collections.calculateBaggagesMass();

        assertEquals(expectedMass,actualMass);
    }
}
