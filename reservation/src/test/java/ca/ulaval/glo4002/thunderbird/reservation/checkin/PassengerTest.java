package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exceptions.InvalidFieldException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerAlreadyCheckedInException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PassengerTest {

    private static final String FIRST_NAME = "Uncle";
    private static final String LAST_NAME = "Bob";
    private static final String PASSPORT_NUMBER = "2564-5424";
    private static final String SEAT_CLASS = "economy";
    private static final int AGE = 18;

    private Passenger validPassenger;

    @Before
    public void setUp() {
        validPassenger = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
    }

    @Test
    public void shouldNotBeCheckedIn() {
        boolean isCheckedIn = validPassenger.isCheckedIn();
        assertFalse(isCheckedIn);
    }

    @Test
    public void whenWeCheckin_shouldBeCheckedIn() {
        validPassenger.checkin();

        boolean isCheckedIn = validPassenger.isCheckedIn();
        assertTrue(isCheckedIn);
    }


    @Test(expected = PassengerAlreadyCheckedInException.class)
    public void givenPassengerAlreadyCheckin_whenWeCheckin_shouldThrowAnException() {
        validPassenger.checkin();

        validPassenger.checkin();
    }

    @Test(expected = InvalidFieldException.class)
    public void givenPassengerWithoutFirstName_shouldThrowAnException() {
        new Passenger("", LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
    }

    @Test(expected = InvalidFieldException.class)
    public void givenPassengerWithoutLastName_shouldThrowAnException() {
        new Passenger(FIRST_NAME, "", AGE, PASSPORT_NUMBER, SEAT_CLASS);
    }

    @Test(expected = InvalidFieldException.class)
    public void givenPassengerWithoutPassportNumber_shouldThrowAnException() {
        new Passenger(FIRST_NAME, LAST_NAME, AGE, "", SEAT_CLASS);
    }
}