package ca.ulaval.glo4002.thunderbird.boarding.persistence.flight;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.AMSSystem;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.PlaneRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

public class HibernateFlightRepositoryIntegrationTest {
    private static final Plane A_PLANE = new Plane("dash-8", 1, 2000);
    private static final Seat A_SEAT = new Seat(1, "A", 56, true, true, 123.45, Seat.SeatClass.ANY, false, true);
    private static final String A_PLANE_MODEL = "dash-8";
    private static final String A_FLIGHT_NUMBER = "QK-918";
    private static final String ANOTHER_FLIGHT_NUMBER = "AB-123";
    private static final Instant A_FLIGHT_DATE = Instant.ofEpochMilli(1478195361);

    private AMSSystem amsSystem;
    private PlaneRepository planeRepository;
    private FlightRepository flightRepository;

    @Before
    public void setUp() {
        amsSystem = mock(AMSSystem.class);
        planeRepository = mock(PlaneRepository.class);
        flightRepository = new HibernateFlightRepository(amsSystem, planeRepository);
    }

    @Test
    public void givenAFlight_whenSaving_shouldBeAbleToRetrieve() {
        willReturn(A_PLANE_MODEL).given(amsSystem).getPlaneModel(anyString());
        List<Seat> seats = new ArrayList<>();
        seats.add(A_SEAT);
        Flight flight = new Flight(A_FLIGHT_NUMBER, A_FLIGHT_DATE, A_PLANE, seats);

        flightRepository.saveFlight(flight);
        Flight fetchedFlight = flightRepository.getFlight(A_FLIGHT_NUMBER, A_FLIGHT_DATE);

        assertFlightEquals(flight, fetchedFlight);
    }

    @Test
    public void givenAFlightNumber_whenFetching_shouldReturn_aFlight() {
        willReturn(A_PLANE_MODEL).given(amsSystem).getPlaneModel(anyString());

        Flight flight = flightRepository.getFlight(ANOTHER_FLIGHT_NUMBER, A_FLIGHT_DATE);

        assertEquals(ANOTHER_FLIGHT_NUMBER, flight.getFlightNumber());
        assertEquals(A_FLIGHT_DATE, flight.getFlightDate());
    }

    private void assertFlightEquals(Flight flight, Flight fetchedFlight) {
        assertEquals(flight.getId(), fetchedFlight.getId());
    }
}