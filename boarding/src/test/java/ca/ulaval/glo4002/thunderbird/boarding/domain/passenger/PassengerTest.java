package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.BaggageAmountAuthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger.BAGGAGE_AMOUNT_AUTHORIZED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PassengerTest {
    private static final UUID VALID_PASSENGER_HASH = UUID.randomUUID();
    private static final Seat.SeatClass VALID_PASSENGER_SEAT_CLASS = Seat.SeatClass.ECONOMY;

    private Passenger passengerWithoutBaggage;
    private Passenger passengerWithMaximalBaggageAmountAuthorized;
    private Baggage baggageMock;

    @Before
    public void setup(){
        passengerWithoutBaggage = new Passenger(VALID_PASSENGER_HASH, VALID_PASSENGER_SEAT_CLASS);
        baggageMock = mock(Baggage.class);

        List<Baggage> baggagesCountAuthorized = new ArrayList<Baggage>();
        for (int i = 0; i < BAGGAGE_AMOUNT_AUTHORIZED; i++) {
            baggagesCountAuthorized.add(baggageMock);
        }

        passengerWithMaximalBaggageAmountAuthorized = new Passenger(VALID_PASSENGER_HASH, VALID_PASSENGER_SEAT_CLASS, baggagesCountAuthorized);
    }

    @Test
    public void givenNewPassenger_whenGettingPassengerID_shouldReturnPassengerID(){
        UUID actualValue = passengerWithoutBaggage.getHash();

        UUID expectedValue = VALID_PASSENGER_HASH;
        assertEquals(expectedValue,actualValue);
    }

    @Test
    public void givenNewPassenger_whenComparingSeatClass_shouldBeTheSameAsTheOneWeInputed(){
        boolean actualValue = passengerWithoutBaggage.isSameSeatClass(VALID_PASSENGER_SEAT_CLASS);

        assertTrue(actualValue);
    }

    @Test
    public void givenNewPassengerWithBaggages_whenGettingBaggagesCount_shouldBeTheSameABaggagesCount() {
        int expectedCount = BAGGAGE_AMOUNT_AUTHORIZED;
        int actualCount = passengerWithMaximalBaggageAmountAuthorized.getBaggagesCount();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void givenNewPassengerWithNoBaggage_whenAddingBaggage_shouldHaveOneBaggage() {
        passengerWithoutBaggage.addBaggage(baggageMock);
        int expectedCount = 1;
        int actualCount = passengerWithoutBaggage.getBaggagesCount();

        assertEquals(expectedCount, actualCount);
    }

    @Test(expected = BaggageAmountAuthorizedException.class)
    public void givenPassengerWithBaggageAmountAuthorized_whenAddingBaggage_shouldNotAddBaggage() {
        passengerWithMaximalBaggageAmountAuthorized.addBaggage(baggageMock);
    }
}