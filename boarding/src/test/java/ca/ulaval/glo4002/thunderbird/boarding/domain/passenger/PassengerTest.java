package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class PassengerTest {
    private static final UUID VALID_PASSENGER_HASH = UUID.randomUUID();
    private static final Seat.SeatClass VALID_PASSENGER_SEAT_CLASS = Seat.SeatClass.ECONOMY;

    private Passenger passengerTest;

    @Before
    public void setup(){
        passengerTest = new Passenger(VALID_PASSENGER_HASH, VALID_PASSENGER_SEAT_CLASS);
    }

    @Test
    public void givenNewPassenger_whenGettingPassengerID_shouldReturnPassengerID(){
        UUID actualValue = passengerTest.getHash();

        UUID expectedValue = VALID_PASSENGER_HASH;
        assertEquals(expectedValue,actualValue);
    }

    @Test
    public void givenNewPassenger_whenComparingSeatClass_shouldBeTheSameAsTheOneWeInputed(){
        boolean actualValue = passengerTest.isSameSeatClass(VALID_PASSENGER_SEAT_CLASS);

        assertTrue(actualValue);
    }

}