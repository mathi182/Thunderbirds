package ca.ulaval.glo4002.thunderbird.boarding.persistence.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightId;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneService;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class InMemoryFlightRepositoryTest {
    private static final Plane A_PLANE = new Plane("dash-8", 1, 2000);
    private static final Seat A_SEAT = new Seat(1, "A", 56, true, true, 123.45, Seat.SeatClass.ECONOMY, false, true);
    private static final String A_PLANE_MODEL = "dash-8";
    private static final String A_FLIGHT_NUMBER = "QK-918";
    private static final Instant A_FLIGHT_DATE = Instant.ofEpochMilli(1478195361);

    private AMSSystem amsSystem;
    private PlaneService planeService;
    private FlightRepository flightRepository;

    @Before
    public void setUp() {
        amsSystem = mock(AMSSystem.class);
        planeService = mock(PlaneService.class);
        flightRepository = new InMemoryFlightRepository(amsSystem, planeService);
    }

    @Test
    public void givenAFlight_whenSaving_shouldBeAbleToSaveAndFetch() {
        FlightId flightId = new FlightId(A_FLIGHT_NUMBER, A_FLIGHT_DATE);
        Flight expectedFlight = new Flight(flightId, A_PLANE, Arrays.asList(A_SEAT));

        flightRepository.saveFlight(expectedFlight);

        Flight actualFlight = flightRepository.getFlight(A_FLIGHT_NUMBER, A_FLIGHT_DATE);
        assertEquals(expectedFlight.getId(), actualFlight.getId());
    }
}