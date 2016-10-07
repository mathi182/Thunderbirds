package ca.ulaval.glo4002.thunderbird.boarding.domain;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerStorage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PassengerStorage.class)
public class SeatAssignationsTest {

    private final String VALID_PASSENGER_HASH = "1203dsa2s";
    private PassengerStorage passengerStorage;

    @Before
    public void setUp() {
        passengerStorage = mock(PassengerStorage.class);
        PowerMockito.mockStatic(PassengerStorage.class);
    }

    @Test
    @Ignore
    public void givenAValidPassengerHash_whenAssigningASeat_shouldGetAPassenger(){
        doThrow(new PassengerNotFoundException(VALID_PASSENGER_HASH))
                .when(PassengerStorage.findByPassengerHash(VALID_PASSENGER_HASH));
    }

    @Test
    public void givenAValidPassenger_whenAssigningASeat_shouldGetASeatNumber() {
        when(PassengerStorage.findByPassengerHash(VALID_PASSENGER_HASH)).thenReturn(passengerStorage);
        String FLIGHT_NUMBER = "a320";
        willReturn(FLIGHT_NUMBER).given(passengerStorage).getFlightNumber();
        String FLIGHT_DATE = "2016-10-30T00:00:00Z";
        willReturn(FLIGHT_DATE).given(passengerStorage).getFlightDate();

        String MODE = "RANDOM";
        SeatAssignations seatAssignations = new SeatAssignations(VALID_PASSENGER_HASH, MODE);
        String seat = seatAssignations.assignSeat();

        assertNotEquals("", seat);
    }
}