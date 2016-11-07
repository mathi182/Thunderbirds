package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class PassengerTest {
    private static final UUID RANDOM_UUID = UUID.randomUUID();

    private Passenger passengerTest;

    @Before
    public void setup(){
        passengerTest = new Passenger(RANDOM_UUID);
    }

    @Test
    public void givenNewPassenger_whenGettingPassengerID_ShouldReturnPassengerID(){
        UUID actualValue = passengerTest.getHash();

        UUID expectedValue = RANDOM_UUID;
        assertEquals(expectedValue,actualValue);
    }

}