package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerNotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;


import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PassengerFetcherTest{
    private static final UUID VALID_PASSENGER_HASH = UUID.fromString("3b99dbde-7fe9-4cbe-91bf-72174ea0d829");
    private static final UUID RANDOM_UUID = UUID.randomUUID();

    private PassengerFetcher passengerFetcherTest;

    @Before
    public void setup(){
        passengerFetcherTest = new PassengerFetcher();
    }

    @Test
    @Ignore //not a unit test, need to start reservation server for it to work
    public void givenNewPassengerFetcher_whenRequestingValidPassenger_ShouldBeCorrectPassenger(){
        Passenger passenger = passengerFetcherTest.fetchPassenger(VALID_PASSENGER_HASH);

        assertEquals(VALID_PASSENGER_HASH,passenger.getHash());
    }

    @Test(expected = PassengerNotFoundException.class)
    @Ignore //not a unit test, need to start reservation server for it to work
    public void givenNewPassengerFetcher_whenRequestingExistingPassenger_ShouldThrowPassengerNotFound(){
        passengerFetcherTest.fetchPassenger(RANDOM_UUID);
    }
}