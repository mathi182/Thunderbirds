package ca.ulaval.glo4002.thunderbird.boarding;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PassengerTest {

    private static final String FIRST_NAME = "Uncle";
    private static final String LAST_NAME = "Bob";
    private static final String PASSPORT_NUMBER = "2564-5424";
    private static final String SEAT_CLASS = "economy";
    private static final int AGE = 18;
    private Passenger newPassengerWithMinimumInformation;
    private Passenger newPassengerWithAllInformation;

    @Before
    public void newPassenger() {

        newPassengerWithAllInformation = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS );
        newPassengerWithMinimumInformation = new Passenger(AGE, SEAT_CLASS);
    }

    @Test
    public void whenCreatingAPassengerWithAllInformations_ShouldHaveSameInformations() {
        assertEquals(newPassengerWithAllInformation.firstName, FIRST_NAME);
        assertEquals(newPassengerWithAllInformation.lastName, LAST_NAME);
        assertEquals(newPassengerWithAllInformation.age, AGE);
        assertEquals(newPassengerWithAllInformation.passportNumber, PASSPORT_NUMBER);
        assertEquals(newPassengerWithAllInformation.seatClass, SEAT_CLASS);

    }

    @Test
    public void whenCreatingAPassenger_ShouldHaveAgeAndSeatClass() {
        assertEquals(newPassengerWithMinimumInformation.age, AGE);
        assertEquals(newPassengerWithMinimumInformation.seatClass, SEAT_CLASS);
    }

    @Test
    public void whenCreatingAPassenger_ShouldGenerateHash(){
        assertNotEquals(newPassengerWithAllInformation.hash, "");
        assertNotEquals(newPassengerWithMinimumInformation.hash, "");
    }
}

