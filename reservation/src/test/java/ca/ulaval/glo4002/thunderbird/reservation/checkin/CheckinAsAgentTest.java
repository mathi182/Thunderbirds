package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions.ReservationNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

public class CheckinAsAgentTest {
    private static final String AGENT_ID = "agentId";
    private static final UUID PASSENGER_HASH = new UUID(1L, 2L);
    private static final Instant TODAYS_DATE = Instant.parse("2016-09-06T13:00:00Z");

    private Checkin checkinAsAgent;
    private Passenger passengerMock;
    private Reservation reservationMock;

    @Before
    public void setup() {
        reservationMock = mock(Reservation.class);
        passengerMock = mock(Passenger.class);
        willReturn(PASSENGER_HASH).given(passengerMock).getId();
        willReturn(false).given(passengerMock).isCheckedIn();
        willReturn(reservationMock).given(passengerMock).getReservation();

        checkinAsAgent = new Checkin(PASSENGER_HASH, AGENT_ID) {
            @Override
            public Passenger getPassenger() {
                return passengerMock;
            }
        };
    }

    @Test
    public void whenCompletingCheckin_shouldCheckinAndSavePassenger() {
        checkinAsAgent.completeCheckin(TODAYS_DATE);

        verify(reservationMock, never()).getFlightDate();
        verify(passengerMock).checkin();
        verify(passengerMock).save();
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenPassengerWithoutReservation_whenCompletingCheckin_shouldNotCompleteCheckin() {
        willReturn(null).given(passengerMock).getReservation();

        checkinAsAgent.completeCheckin(TODAYS_DATE);
    }
}