package ca.ulaval.glo4002.thunderbird.boarding.rest.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignations;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerNotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.doThrow;

@RunWith(PowerMockRunner.class)
public class SeatAssignationsResourceTest {
    private static final String SEAT_ASSIGNATION_URI = "/seat-assignations/666";
    private static final int SEAT_ASSIGNATION_ID = 666;

    private SeatAssignationsResource seatAssignationsResource;
    private SeatAssignations seatAssignations;

    @PrepareForTest
    public void preparation() {
        PowerMockito.mockStatic(Passenger.class);
    }

    @Before
    public void setUp() {
        seatAssignationsResource = new SeatAssignationsResource();

        seatAssignations = mock(SeatAssignations.class);
        willReturn(SEAT_ASSIGNATION_ID).given(seatAssignations).getId();
    }

    @Test
    @Ignore
    public void givenInvalidPassengerHash_whenAssigningSeat_shouldReturnNotFound() {
        String PASSENGER_HASH = "12345";
        doThrow(new PassengerNotFoundException(PASSENGER_HASH)).when(Passenger.findByPassengerHash(PASSENGER_HASH));

        Response responseActual = seatAssignationsResource.assignSeat(seatAssignations);

        int statusActual = responseActual.getStatus();
        assertEquals(NOT_FOUND.getStatusCode(), statusActual);
    }

    @Test
    public void whenAssigningASeat_shouldSeatAssignationCreated() {
        Response responseActual = seatAssignationsResource.assignSeat(seatAssignations);

        int statusActual = responseActual.getStatus();
        String locationActual = responseActual.getLocation().toString();
        assertEquals(CREATED.getStatusCode(), statusActual);
        assertEquals(SEAT_ASSIGNATION_URI, locationActual);
    }
}