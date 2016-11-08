package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;


import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PassengerFetcherTest{
    private static final UUID VALID_PASSENGER_HASH = UUID.fromString("bcae2541-b980-4dd0-a351-16f4d9966e07");

    private PassengerFetcher passengerFetcherTest;

    @Before
    public void setup(){
        passengerFetcherTest = new PassengerFetcher();

    }

    @Test
    @Ignore //Not a unit test, cannot be used without starting reservation Server and fetching UUID
    public void givenNewPassengerFetcher_whenRequestingPassenger_ShouldBeCorrectPassenger(){
        Passenger passenger = passengerFetcherTest.fetchPassenger(VALID_PASSENGER_HASH);

        assertEquals(VALID_PASSENGER_HASH,passenger.getHash());
    }
}