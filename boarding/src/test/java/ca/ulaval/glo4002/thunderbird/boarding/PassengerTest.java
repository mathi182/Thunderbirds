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
    private Passenger newPassenger;

    @Before
    public void newPassenger() {
        newPassenger = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
    }

    @Test
    public void whenCreatingAPassenger_ShouldGenerateHash() {
        assertNotEquals(newPassenger.getHash(), "");
    }
}

