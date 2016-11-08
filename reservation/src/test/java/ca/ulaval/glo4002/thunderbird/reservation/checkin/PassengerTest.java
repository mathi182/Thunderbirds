package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.TestConfig;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerAlreadyCheckedInException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PassengerTest {
    private Passenger passenger;

    @Before
    public void setUp() {
        passenger = TestConfig.getDefaultPassenger();
    }

    @Test
    public void shouldNotBeCheckedIn() {
        boolean isCheckedIn = passenger.isCheckedIn();
        assertFalse(isCheckedIn);
    }

    @Test
    public void whenWeCheckin_shouldBeCheckedIn() {
        passenger.checkin();

        boolean isCheckedIn = passenger.isCheckedIn();
        assertTrue(isCheckedIn);
    }

    @Test(expected = PassengerAlreadyCheckedInException.class)
    public void givenPassengerAlreadyCheckin_whenWeCheckin_shouldThrowAnException() {
        passenger.checkin();

        passenger.checkin();
    }
}