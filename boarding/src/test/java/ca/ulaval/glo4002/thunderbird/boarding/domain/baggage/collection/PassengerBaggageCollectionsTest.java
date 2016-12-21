package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.MedicalBaggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class PassengerBaggageCollectionsTest {

    private static final UUID PASSENGER_UUID = UUID.randomUUID();
    private PassengerBaggageCollections collections;

    @Test
    public void _() {
        Passenger passenger = mock(Passenger.class);
        collections = new PassengerBaggageCollections(passenger);


        Baggage baggage = mock(MedicalBaggage.class);
        willReturn(false).given(baggage).hasSpecialities();
        willReturn("medical").given(baggage).getType();

        collections.addBaggage(baggage);

        assertEquals(1, collections.collection.size());
    }
}
