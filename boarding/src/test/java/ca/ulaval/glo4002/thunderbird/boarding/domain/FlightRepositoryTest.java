package ca.ulaval.glo4002.thunderbird.boarding.domain;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlightRepositoryTest {

    private FlightRepository repository = FlightRepository.INSTANCE;

    private static final String NEW_FLIGHT_NUMBER = "RD3213";
    private static final String DASH_FLIGHT_NUMBER = "QK-918";
    private static final String A320_FLIGHT_NUMBER = "NK-750";
    private static final String BOEING_FLIGHT_NUMBER = "QK-432";
    private static final String BOEING_MODEL = "boeing-777-300";
    private static final String DASH_MODEL = "dash-8";
    private static final String A320_MODEL = "a320";

    @After
    public void tearDown() {
        repository.clear();
    }

    @Test
    public void givenANewFlightNumber_whenRetrievingTheModel_shouldRepositoryContainTheFlight() {
        repository.getPlaneModel(NEW_FLIGHT_NUMBER);
        boolean planeModelContainsFlightNumber = repository.contains(NEW_FLIGHT_NUMBER);

        assertTrue(planeModelContainsFlightNumber);
    }

    @Test
    public void givenANewFlightNumber_whenRetrievingTheModel_shouldHaveAPlaneModel() {
        Object planeModelReceived = repository.getPlaneModel(NEW_FLIGHT_NUMBER);
        
        assertNotNull(planeModelReceived);
    }

    @Test
    public void givenANonEmptyRepository_whenClearingTheRepository_shouldBeEmpty() {
        repository.getPlaneModel(NEW_FLIGHT_NUMBER);

        repository.clear();

        boolean isSizeEmpty = repository.size() == 0;
        assertTrue(isSizeEmpty);
    }

    @Test
    public void whenUsingDemoSetup_shouldHaveSpecificFlightModelInRepository() {
        repository.demoSetup();

        boolean repositoryContainsBoeingFlight = repository.contains(BOEING_FLIGHT_NUMBER);
        boolean repositoryContainsA320Flight = repository.contains(A320_FLIGHT_NUMBER);
        boolean repositoryContainsDashFlight = repository.contains(DASH_FLIGHT_NUMBER);
        Object planeModelBoeing = repository.getPlaneModel(BOEING_FLIGHT_NUMBER);
        Object planeModelDash = repository.getPlaneModel(DASH_FLIGHT_NUMBER);
        Object planeModelA320 = repository.getPlaneModel(A320_FLIGHT_NUMBER);
        assertTrue(repositoryContainsBoeingFlight);
        assertTrue(repositoryContainsA320Flight);
        assertTrue(repositoryContainsDashFlight);
        assertEquals(planeModelBoeing, BOEING_MODEL);
        assertEquals(planeModelDash, DASH_MODEL);
        assertEquals(planeModelA320, A320_MODEL);
    }
}