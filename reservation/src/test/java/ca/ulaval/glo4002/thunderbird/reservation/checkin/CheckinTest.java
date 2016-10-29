package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.Instant;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Reservation.class, Passenger.class})
public class CheckinTest {
    private static final int RESERVATION_NUMBER = 15;
    private static final String AGENT_ID = "agentId";
    private static final String SELF_CHECKING = "SELF";
    private static final String PASSENGER_HASH_WITH_RESERVATION = "passenger_hash_with_reservation";
    private static final Instant TODAYS = ISO_INSTANT.parse("2016-09-06T13:00:00Z", Instant::from);

    private Passenger passengerMock;
    private Reservation reservationMock;

    @Before
    public void setup() {
        mockStatic(Reservation.class);
        mockStatic(Passenger.class);

        reservationMock = mock(Reservation.class);
        passengerMock = mock(Passenger.class);

        given(Reservation.findByReservationNumber(RESERVATION_NUMBER)).willReturn(reservationMock);
        given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);
        willReturn(RESERVATION_NUMBER).given(passengerMock).getReservationNumber();
    }

    @Test
    public void givenAgentCheckin_whenCompletingCheckin_shouldCheckInAndSavePassenger() {
        Checkin agentCheckin = new Checkin(PASSENGER_HASH_WITH_RESERVATION, AGENT_ID);

        agentCheckin.completePassengerCheckin(TODAYS);

        verify(passengerMock).checkin();
        verify(passengerMock).save();
    }

    @Test
    public void givenSelfCheckinAndValidDate_whenCompletingCheckin_shouldCheckInAndSavePassenger() {
        Checkin selfCheckin = new Checkin(PASSENGER_HASH_WITH_RESERVATION, SELF_CHECKING);
        Instant validForCheckinFlight = ISO_INSTANT.parse("2016-09-06T21:00:00Z", Instant::from);
        willReturn(validForCheckinFlight).given(reservationMock).getFlightDate();

        selfCheckin.completePassengerCheckin(TODAYS);

        verify(passengerMock).checkin();
        verify(passengerMock).save();
    }

    @Test(expected = CheckinNotOnTimeException.class)
    public void givenSelfCheckinAndTooEarlyDate_whenCompletingCheckin_shouldNotCompleteCheckin() {
        Checkin selfCheckin = new Checkin(PASSENGER_HASH_WITH_RESERVATION, SELF_CHECKING);
        Instant tooEarlyForCheckinFlight = ISO_INSTANT.parse("2016-09-02T21:00:00Z", Instant::from);
        willReturn(tooEarlyForCheckinFlight).given(reservationMock).getFlightDate();

        selfCheckin.completePassengerCheckin(TODAYS);
    }

    @Test(expected = CheckinNotOnTimeException.class)
    public void givenSelfCheckinAndTooLateDate_whenCompletingCheckin_shouldNotCompleteCheckin() {
        Checkin selfCheckin = new Checkin(PASSENGER_HASH_WITH_RESERVATION, SELF_CHECKING);
        Instant tooLateForCheckinFlight = ISO_INSTANT.parse("2016-09-06T14:00:00Z", Instant::from);
        willReturn(tooLateForCheckinFlight).given(reservationMock).getFlightDate();

        selfCheckin.completePassengerCheckin(TODAYS);
    }
}