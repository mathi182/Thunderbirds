package ca.ulaval.glo4002.thunderbird.boarding.domain;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlightRepositoryTest {

    private static final FlightRepository repository = new FlightRepository();
    private static final String NEW_FLIGHT_NUMBER = "RD3213";
    private static final String DASH_FLIGHT_NUMBER = "QK-918";
    private static final String A320_FLIGHT_NUMBER = "NK-750";
    private static final String BOEING_FLIGHT_NUMBER = "QK-432";
    private static final String BOEING_MODEL = "boeing-777-300";
    private static final String DASH_MODEL = "dash-8";
    private static final String A320_MODEL = "a320";

    @After
    public void setUp() {
        repository.clear();
    }

    @Test
    public void givenANewFlightNumber_whenAddingToTheFlightRepository_shouldContainTheFlight() {
        repository.addFlight(NEW_FLIGHT_NUMBER);
        assertTrue(repository.contains(NEW_FLIGHT_NUMBER));
    }

    @Test
    public void givenANewFlightNumber_whenAddingToTheFlightRepository_shouldHaveAPlaneModel() {
        repository.addFlight(NEW_FLIGHT_NUMBER);
        assertNotNull(repository.getPlaneModel(NEW_FLIGHT_NUMBER));
    }

    @Test
    public void givenANewFlightNumber_whenRetrievingTheModel_shouldReturnNull() {
        String actualResult = repository.getPlaneModel(NEW_FLIGHT_NUMBER);

        assertNull(actualResult);
    }

    @Test
    public void givenANonEmptyRepository_whenClearingTheRepository_shouldBeEmpty() {
        repository.addFlight(NEW_FLIGHT_NUMBER);

        repository.clear();

        assertTrue(repository.size() == 0);
    }

    @Test
    public void whenUsingDemoSetup_shouldHaveSpecificFlightModelInRepository() {
        repository.demoSetup();

        assertTrue(repository.contains(BOEING_FLIGHT_NUMBER));
        assertTrue(repository.contains(DASH_FLIGHT_NUMBER));
        assertTrue(repository.contains(A320_FLIGHT_NUMBER));
        assertEquals(repository.getPlaneModel(BOEING_FLIGHT_NUMBER),BOEING_MODEL);
        assertEquals(repository.getPlaneModel(DASH_FLIGHT_NUMBER),DASH_MODEL);
        assertEquals(repository.getPlaneModel(A320_FLIGHT_NUMBER),A320_MODEL);
    }



}