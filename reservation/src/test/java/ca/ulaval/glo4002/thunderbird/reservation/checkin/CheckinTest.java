package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.HOURS;
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
    private static final Instant TODAYS_DATE = Instant.now();
    private static final int MAX_LATE_CHECKIN_IN_HOUR = 6;
    private static final int MAX_EARLY_CHECKIN_IN_HOUR = 48;

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

        agentCheckin.completePassengerCheckin(TODAYS_DATE);

        verify(passengerMock).checkin();
        verify(passengerMock).save();
    }

    @Test
    public void givenValidFlightDateLateLimit_whenCompletingCheckinOnline_shouldCheckInAndSavePassenger() {
        this.testValidCompletePassengerCheckinOnline(MAX_LATE_CHECKIN_IN_HOUR);
    }

    @Test
    public void givenValidFlightDateEarlyLimit_whenCompletingCheckinOnline_shouldCheckinAndSavePassenger() throws Exception {
        this.testValidCompletePassengerCheckinOnline(MAX_EARLY_CHECKIN_IN_HOUR);
    }

    private void testValidCompletePassengerCheckinOnline(int hoursBeforeFlight) {
        Checkin selfCheckin = new Checkin(PASSENGER_HASH_WITH_RESERVATION, SELF_CHECKING);
        Instant validFlightDate = TODAYS_DATE.plus(hoursBeforeFlight, HOURS);
        willReturn(validFlightDate).given(reservationMock).getFlightDate();

        selfCheckin.completePassengerCheckin(TODAYS_DATE);

        verify(passengerMock).checkin();
        verify(passengerMock).save();
    }

    @Test(expected = CheckinNotOnTimeException.class)
    public void givenTooEarlyFlightDate_whenCompletingCheckinOnline_shouldNotCompleteCheckin() {
        Checkin selfCheckin = new Checkin(PASSENGER_HASH_WITH_RESERVATION, SELF_CHECKING);
        Instant tooEarlyFlightDate = TODAYS_DATE.plus(MAX_EARLY_CHECKIN_IN_HOUR + 1, HOURS);
        willReturn(tooEarlyFlightDate).given(reservationMock).getFlightDate();

        selfCheckin.completePassengerCheckin(TODAYS_DATE);
    }

    @Test(expected = CheckinNotOnTimeException.class)
    public void givenTooLateFlightDate_whenCompletingCheckinOnline_shouldNotCompleteCheckin() {
        Checkin selfCheckin = new Checkin(PASSENGER_HASH_WITH_RESERVATION, SELF_CHECKING);
        Instant tooLateFlightDate = TODAYS_DATE.plus(MAX_LATE_CHECKIN_IN_HOUR - 1, HOURS);
        willReturn(tooLateFlightDate).given(reservationMock).getFlightDate();

        selfCheckin.completePassengerCheckin(TODAYS_DATE);
    }
}