package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PassengerITest {
    private static final UUID NON_EXISTENT_PASSENGER_HASH = new UUID(1L, 2L);

    private static final String FIRST_NAME = "Uncle";
    private static final String LAST_NAME = "Bob";
    private static final String PASSPORT_NUMBER = "2564-5424";
    private static final String SEAT_CLASS = "economy";
    private static final int AGE = 18;

    private Passenger passenger;

    @Before
    public void setUp() {
        passenger = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
    }

    @Test
    public void whenAPassengerIsSaved_shouldBeAbleToRetrieve() {
        passenger.save();

        Passenger retrievedPassenger = Passenger.findByPassengerHash(passenger.getId());
        assertEquals(passenger.getId().toString(), retrievedPassenger.getId().toString());
    }

    @Test(expected = PassengerNotFoundException.class)
    public void whenFinding_shouldThrowNotFound() {
        Passenger.findByPassengerHash(NON_EXISTENT_PASSENGER_HASH);
    }
}