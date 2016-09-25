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
    private String NON_EXISTENT_HASH = "0";

    @Before
    public void newPassenger() {
        newPassengerWithAllInformation = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS );
        newPassengerWithMinimumInformation = new Passenger(AGE, SEAT_CLASS);
        newPassengerWithoutPassportNumber = new Passenger(FIRST_NAME, LAST_NAME, AGE, "", SEAT_CLASS);
        newPassengerWithoutLastName = new Passenger(FIRST_NAME, "", AGE, PASSPORT_NUMBER, SEAT_CLASS);
        newPassengerWithoutFirstName = new Passenger("", LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
    }

    @Test
    public void whenCreatingAPassengerWithAllInformations_thenShouldHaveSameInformations() {
        assertEquals(newPassengerWithAllInformation.firstName, FIRST_NAME);
        assertEquals(newPassengerWithAllInformation.lastName, LAST_NAME);
        assertEquals(newPassengerWithAllInformation.age, AGE);
        assertEquals(newPassengerWithAllInformation.passportNumber, PASSPORT_NUMBER);
        assertEquals(newPassengerWithAllInformation.seatClass, SEAT_CLASS);
    }

    @Test
    public void whenCreatingAPassengerWithMinimumInformations_thenShouldHaveAgeAndSeatClass() {
        assertEquals(newPassengerWithMinimumInformation.age, AGE);
        assertEquals(newPassengerWithMinimumInformation.seatClass, SEAT_CLASS);
    }

    @Test
    public void whenCreatingAPassenger_thenShouldGenerateHash(){
        assertNotEquals(newPassengerWithAllInformation.getHash(), "");
        assertNotEquals(newPassengerWithMinimumInformation.getHash(), "");
    }

    @Test
    public void givenValidPassengerForCheckin_whenIsValidForCheckin_thenShouldReturnTrue() throws Exception {
        assertTrue(newPassengerWithAllInformation.isValidForCheckin());
    }

    @Test
    public void givendPassengerWithoutFirstName_whenIsValidForCheckin_thenShouldReturnFalse() throws Exception {
        assertFalse(newPassengerWithoutFirstName.isValidForCheckin());
    }

    @Test
    public void givendPassengerWithoutLastName_whenIsValidForCheckin_thenShouldReturnFalse() throws Exception {
        assertFalse(newPassengerWithoutLastName.isValidForCheckin());
    }

    @Test
    public void givenPassengerWithoutPassportNumber_whenIsValidForCheckin_thenShouldReturnFalse() throws Exception {
        assertFalse(newPassengerWithoutPassportNumber.isValidForCheckin());
    }
}

