package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;


public class PassengerRequestTest{
    private static final UUID PASSENGER_ID = UUID.randomUUID();

    @Test
    public void givenNewPassengerRequest_WhenGetPassengerID_UUIDShouldBeTheSameAsTheOneWeInputed(){
        PassengerRequest test = new PassengerRequest(PASSENGER_ID);
        UUID actualValue = test.getPassengerID();

        UUID expectedValue = PASSENGER_ID;
        assertEquals(expectedValue,actualValue);
    }
}