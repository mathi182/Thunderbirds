package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerNotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.Instant;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Passenger.class)
public class SeatAssignationsTest {

    private final String VALID_PASSENGER_HASH = "1203dsa2s";
    private Passenger passenger;

    @Before
    public void setUp() {
        passenger = mock(Passenger.class);
        PowerMockito.mockStatic(Passenger.class);
    }

    @Test
    @Ignore
    public void givenAValidPassengerHash_whenAssigningASeat_shouldGetAPassenger() {
        doThrow(new PassengerNotFoundException(VALID_PASSENGER_HASH))
                .when(Passenger.findByPassengerHash(VALID_PASSENGER_HASH));
    }

    @Test
    public void givenAValidPassenger_whenAssigningASeat_shouldGetASeatNumber() {
        when(Passenger.findByPassengerHash(VALID_PASSENGER_HASH)).thenReturn(passenger);
        String FLIGHT_NUMBER = "a320";
        willReturn(FLIGHT_NUMBER).given(passenger).getFlightNumber();
        Instant FLIGHT_DATE = ISO_INSTANT.parse("2016-10-30T00:00:00Z", Instant::from);
        willReturn(FLIGHT_DATE).given(passenger).getFlightDate();
        
        String MODE = "RANDOM";
        SeatAssignations seatAssignations = new SeatAssignations(VALID_PASSENGER_HASH, MODE);
        String seat = seatAssignations.assignSeat();

        assertNotEquals("", seat);
    }
}
