package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.exceptions.CheckinNotOnTimeException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions.ReservationNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.HOURS;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CheckinAsSelfTest {
    private static final UUID PASSENGER_HASH = new UUID(1L, 2L);
    private static final Instant FLIGHT_DATE = Instant.parse("2016-09-06T13:00:00Z");
    private static final int MAX_LATE_CHECKIN_IN_HOUR = 6;
    private static final int MAX_EARLY_CHECKIN_IN_HOUR = 48;

    private Checkin checkinAsSelf;
    private Passenger passengerMock;

    @Before
    public void setup() {
        Reservation reservationMock = mock(Reservation.class);
        willReturn(FLIGHT_DATE).given(reservationMock).getFlightDate();

        passengerMock = mock(Passenger.class);
        willReturn(PASSENGER_HASH).given(passengerMock).getId();
        willReturn(false).given(passengerMock).isCheckedIn();
        willReturn(reservationMock).given(passengerMock).getReservation();

        checkinAsSelf = new Checkin(PASSENGER_HASH, Checkin.SELF) {
            @Override
            public Passenger getPassenger() {
                return passengerMock;
            }
        };
    }

    @Test
    public void givenValidFlightDateLateLimit_whenCompletingCheckin_shouldCheckinAndSavePassenger() {
        Instant validDate = FLIGHT_DATE.minus(MAX_EARLY_CHECKIN_IN_HOUR, HOURS);

        checkinAsSelf.completeCheckin(validDate);

        verify(passengerMock).checkin();
        verify(passengerMock).save();
    }

    @Test
    public void givenValidFlightDateEarlyLimit_whenCompletingCheckin_shouldCheckinAndSavePassenger() {
        Instant validDate = FLIGHT_DATE.minus(MAX_LATE_CHECKIN_IN_HOUR, HOURS);

        checkinAsSelf.completeCheckin(validDate);

        verify(passengerMock).checkin();
        verify(passengerMock).save();
    }

    @Test(expected = CheckinNotOnTimeException.class)
    public void givenTooEarlyFlightDate_whenCompletingCheckin_shouldNotCompleteCheckin() {
        Instant invalidDate = FLIGHT_DATE.minus(MAX_EARLY_CHECKIN_IN_HOUR + 1, HOURS);

        checkinAsSelf.completeCheckin(invalidDate);
    }

    @Test(expected = CheckinNotOnTimeException.class)
    public void givenTooLateFlightDate_whenCompletingCheckin_shouldNotCompleteCheckin() {
        Instant invalidDate = FLIGHT_DATE.minus(MAX_LATE_CHECKIN_IN_HOUR - 1, HOURS);

        checkinAsSelf.completeCheckin(invalidDate);
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenPassengerWithoutReservation_whenCompletingCheckin_shouldNotCompleteCheckin() {
        willReturn(null).given(passengerMock).getReservation();

        checkinAsSelf.completeCheckin(FLIGHT_DATE);
    }
}