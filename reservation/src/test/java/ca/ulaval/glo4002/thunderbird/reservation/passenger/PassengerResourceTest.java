package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PassengerResourceTest {

    private static final UUID PASSENGER_HASH = UUID.randomUUID();

    private PassengerResource passengerResourceTest;
    private Passenger passengerMock;
    UUID passengerID;

    @Before
    public void setup(){
        passengerResourceTest = Mockito.spy(PassengerResource.class);
        passengerMock = mock(Passenger.class);
    }

    @Test(expected = PassengerNotFoundException.class)
    public void givenNewPassengerResource_whenGettingInexistingPassenger_ShouldThrowPassengerNotFound(){
        Mockito.doThrow(PassengerNotFoundException.class).when(passengerResourceTest).getPassenger(PASSENGER_HASH);

        passengerResourceTest.fetchPassenger(PASSENGER_HASH);
    }

    @Test
    public void givenNewPassengerResource_whenGettingExistingPassenger_ShouldReturnPassenger(){
        Mockito.doReturn(passengerMock).when(passengerResourceTest).getPassenger(PASSENGER_HASH);

        Passenger actualValue = passengerResourceTest.fetchPassenger(PASSENGER_HASH);

        Passenger expectedValue = passengerMock;
        assertEquals(expectedValue,actualValue);
    }

}