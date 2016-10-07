package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Reservation.class, Passenger.class})
public class CheckinTest {
    private static final String PASSENGER_HASH = "passenger_hash";
    public static final String AGENT_ID = "agentId";
    public static final String SELF_CHECKING = "SELF";
    public static final String PASSENGER_HASH_WITH_RESERVATION = "passenger_hash_with_reservation";
    public static final String PASSENGER_HASH_WITH_INVALID_PASSENGER = "passenger_hash_with_invalid_passenger";
    public static final String PASSENGER_HASH_WITH_INVALID_SELF_CHECKIN = "passenger_hash_with_invalid_self_checkin";

    private Checkin checkinValid;
    private Checkin checkinNull;
    private Checkin checkinWithInvalidPassenger;

    private Passenger passengerMock;

    @Before
    public void setup() throws Exception{
        PowerMockito.mockStatic(Reservation.class);
        PowerMockito.mockStatic(Passenger.class);

        this.checkinValid = new Checkin(PASSENGER_HASH_WITH_RESERVATION, AGENT_ID);
        this.checkinNull = new Checkin(null, null);
        this.checkinWithInvalidPassenger = new Checkin(PASSENGER_HASH_WITH_INVALID_PASSENGER,AGENT_ID);

        passengerMock = mock(Passenger.class);
    }

    @Test
    public void whenCreatingNewSelfCheckin_ShouldBeSelfCheckin() throws Exception{
        Checkin checkintest = new Checkin(PASSENGER_HASH,SELF_CHECKING);

        assertTrue(checkintest.isSelfCheckin());
    }

    @Test
    public void whenCreatingNewCheckin_ShouldNotBeSelfCheckin() throws Exception{
        Checkin checkintest = new Checkin(PASSENGER_HASH,AGENT_ID);

        assertFalse(checkintest.isSelfCheckin());
    }

    @Test
    public void givenCheckinValid_WhenIsComplete_ShouldBeComplete() throws Exception{
        assertTrue(checkinValid.isComplete());
    }

    @Test
    public void givenIncompleteCheckin_whenIsComplte_ShouldNotBeComplete() throws Exception{
        assertFalse(checkinNull.isComplete());
    }
    @Test
    public void givenCheckinValid_WhenPassengerExist_PassengerShouldExist() throws Exception{
        BDDMockito.given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);

        assertTrue(checkinValid.passengerExist());
    }

    @Test
    public void givenCheckinValid_WhenPassengerDoesNotExist_PassengerShouldNotExist() throws Exception{
        BDDMockito.given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willThrow(new PassengerNotFoundException(PASSENGER_HASH_WITH_RESERVATION));

        assertFalse(checkinValid.passengerExist());
    }

    @Test
    public void givenCheckinValid_WhenIsValid_ShouldBeValid() throws Exception{
        BDDMockito.given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);
        willReturn(true).given(passengerMock).isValidForCheckin();

        assertTrue(checkinValid.isValid());
    }

    @Test
    public void givenCheckinWithInvalidPassenger_WhenIsValid_ShouldNotBeValid() throws Exception{
        BDDMockito.given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_INVALID_PASSENGER)).willReturn(passengerMock);
        willReturn(false).given(passengerMock).isValidForCheckin();

        assertFalse(checkinWithInvalidPassenger.isValid());
    }
}