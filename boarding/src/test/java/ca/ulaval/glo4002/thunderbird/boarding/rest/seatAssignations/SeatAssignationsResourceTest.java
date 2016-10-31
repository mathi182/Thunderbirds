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

import static ca.ulaval.glo4002.thunderbird.boarding.rest.RestTestConfig.givenBaseRequest;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.mockito.BDDMockito.willReturn;
import static org.powermock.api.mockito.PowerMockito.doThrow;

@RunWith(PowerMockRunner.class)
public class SeatAssignationsResourceTest {
    // TODO:
//    private static final int SEAT_ASSIGNATION_ID = 666;
//    private static final String SEAT_ASSIGNATION_URI = SeatAssignationsResource.PATH + SEAT_ASSIGNATION_ID;
//
//    private SeatAssignations seatAssignations;
//
//    @PrepareForTest
//    public void preparation() {
//        PowerMockito.mockStatic(Passenger.class);
//    }
//
//    @Before
//    public void setUp() {
//        seatAssignations = new SeatAssignations("passengerHash", "mode");
//        willReturn(SEAT_ASSIGNATION_ID).given(seatAssignations).getId();
//    }
//
//    @Test
//    @Ignore
//    public void givenInvalidPassengerHash_whenAssigningSeat_shouldReturnNotFound() {
//        String PASSENGER_HASH = "12345";
//        doThrow(new PassengerNotFoundException(PASSENGER_HASH))
//                .when(Passenger.findByPassengerHash(PASSENGER_HASH));
//
//        givenBaseRequest()
//                .body(seatAssignations)
//                .when()
//                .post(SeatAssignationsResource.PATH)
//                .then()
//                .statusCode(NOT_FOUND.getStatusCode());
//    }
//
//    @Test
//    public void whenAssigningASeat_shouldReturnCreated() {
//        givenBaseRequest()
//                .body(seatAssignations)
//                .when()
//                .post(SeatAssignationsResource.PATH)
//                .then()
//                .statusCode(CREATED.getStatusCode());
//    }

    // TODO :
//    @Test
//    public void whenAssiningASeat_locationShouldBeAdequate() throws Exception {
//
//    }
}