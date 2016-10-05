package ca.ulaval.glo4002.thunderbird.boarding.domain;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
public class SeatAssignationsResourceTest {

    private PassengerStorage passenger;
    private String PASSENGER_HASH = "12345";
    private SeatAssignations seatAssignations;

    private static final String SEAT_ASSIGNATION_URI = "/seat-assignations/666";
    private static final int SEAT_ASSIGNATION_ID = 666;

    @InjectMocks
    SeatAssignationsResource seatAssignationsResource;

    @Before
    public void setUp() {
        passenger = mock(PassengerStorage.class);
        seatAssignations = mock(SeatAssignations.class);
    }

    @Test
    public void whenAssigningASeat_shouldReturnASeat() {
        Response responseActual = seatAssignationsResource.assignSeat(seatAssignations);
        //Fuck it pour la
    }

    @Test
    public void givenReservationFull_whenAssigningSeat_shouldReturnBadRequest() {

    }

    @Test
    public void givenInvalidPassengerHash_whenAssigningSeat_shouldReturnNotFound() {
//        willThrow(PassengerNotFoundException.class).given(passenger).findByPassengerHash(PASSENGER_HASH);
//        Response responseActual = seatAssignationsResource.assignSeat(seatAssignations);
    }


    @Test
    public void whenAssigningASeat_shouldSeatAssignationCreated() {
        willReturn(SEAT_ASSIGNATION_ID).given(seatAssignations).getId();

        Response responseActual = seatAssignationsResource.assignSeat(seatAssignations);
        int statusActual = responseActual.getStatus();
        String locationActual = responseActual.getLocation().toString();

        String locationExpected = SEAT_ASSIGNATION_URI;
        int statusExpected = CREATED.getStatusCode();
        assertEquals(statusExpected, statusActual);
        assertEquals(locationExpected, locationActual);
    }


}
