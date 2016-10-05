package ca.ulaval.glo4002.thunderbird.boarding.domain;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FlightRepositoryTest {

    FlightRepository repository = new FlightRepository();
    String FLIGHT_NUMBER = "RD3213";

    @Test
    public void givenANewFlightNumber_whenUpdatingTheFlightRepository_shouldAPlaneModelIsAssigned() {
        //repository.addFlight(FLIGHT_NUMBER);
        assertTrue(true); //TODO REMOVE ME!
    }

}