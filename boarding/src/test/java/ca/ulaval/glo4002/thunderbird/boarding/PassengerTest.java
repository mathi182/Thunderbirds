package ca.ulaval.glo4002.thunderbird.boarding;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PassengerTest {
    private static final String FIRST_NAME = "Uncle";
    private static final String LAST_NAME = "Bob";
    private static final String PASSPORT_NUMBER = "2564-5424";
    private static final String SEAT_CLASS = "economy";
    private static final int AGE = 18;
    private Passenger newPassengerWithMinimumInformation;
    private Passenger newPassengerWithAllInformation;
    private Passenger newPassengerWithoutFirstName;
    private Passenger newPassengerWithoutLastName;
    private Passenger newPassengerWithoutPassportNumber;

    @Before
    public void newPassenger() {
        newPassengerWithAllInformation = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS );
        newPassengerWithMinimumInformation = new Passenger(AGE, SEAT_CLASS);
        newPassengerWithoutPassportNumber = new Passenger(FIRST_NAME, LAST_NAME, AGE, "", SEAT_CLASS);
        newPassengerWithoutLastName = new Passenger(FIRST_NAME, "", AGE, PASSPORT_NUMBER, SEAT_CLASS);
        newPassengerWithoutFirstName = new Passenger("", LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
    }

    @Test
    public void whenCreatingAPassengerWithMinimumInformations_shouldHaveAgeAndSeatClass() {
        assertEquals(newPassengerWithMinimumInformation.age, AGE);
        assertEquals(newPassengerWithMinimumInformation.seatClass, SEAT_CLASS);
    }

    @Test
    public void givenValidPassengerForCheckin_whenIsValidForCheckin_shouldReturnTrue() throws Exception {
        assertTrue(newPassengerWithAllInformation.isValidForCheckin());
    }

    @Test
    public void givenPassengerWithoutFirstName_whenIsValidForCheckin_shouldReturnFalse() throws Exception {
        assertFalse(newPassengerWithoutFirstName.isValidForCheckin());
    }

    @Test
    public void givenPassengerWithoutLastName_whenIsValidForCheckin_shouldReturnFalse() throws Exception {
        assertFalse(newPassengerWithoutLastName.isValidForCheckin());
    }

    @Test
    public void givenPassengerWithoutPassportNumber_whenIsValidForCheckin_shouldReturnFalse() throws Exception {
        assertFalse(newPassengerWithoutPassportNumber.isValidForCheckin());
    }
}

