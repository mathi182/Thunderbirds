package ca.ulaval.glo4002.thunderbird.reservation.rest.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.infrastructure.util.DateLong;
import ca.ulaval.glo4002.thunderbird.reservation.rest.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.rest.reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Reservation.class, Passenger.class, DateLong.class})
public class CheckinTest {
    private static final int RESERVATION_NUMBER = 15;
    private static final String AGENT_ID = "agentId";
    private static final String SELF_CHECKING = "SELF";
    private static final String PASSENGER_HASH_WITH_RESERVATION = "passenger_hash_with_reservation";
    private static final String PASSENGER_HASH_WITH_INVALID_PASSENGER = "passenger_hash_with_invalid_passenger";
    private static final long TODAYS_DATE = 1473166800000L; // 2016-09-06T13:00
    private static final String VALID_FOR_CHECKIN_FLIGHT_DATE = "2016-09-06T21:00:00Z";
    private static final String TOO_LATE_FOR_CHECKIN_FLIGHT_DATE = "2016-09-06T14:00:00Z";
    private static final String TOO_EARLY_FOR_CHECKIN_FLIGHT_DATE = "2016-09-02T21:00:00Z";

    private Checkin checkinValid;
    private Checkin checkinWithInvalidPassenger;
    private Passenger passengerMock;
    private Reservation reservationMock;

    @Before
    public void setup() {
        mockStatic(Reservation.class);
        mockStatic(DateLong.class);
        mockStatic(Passenger.class);

        passengerMock = mock(Passenger.class);

        checkinValid = new Checkin(PASSENGER_HASH_WITH_RESERVATION, AGENT_ID);
        checkinWithInvalidPassenger = new Checkin(PASSENGER_HASH_WITH_INVALID_PASSENGER, AGENT_ID);

        reservationMock = mock(Reservation.class);

        given(DateLong.getLongCurrentDate()).willReturn(TODAYS_DATE);
    }

    @Test
    public void givenValidCheckin_whenCompletingPassengerCheckin_shouldCompleteCheckin() {
        given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);
        willReturn(RESERVATION_NUMBER).given(passengerMock).getReservationNumber();
        given(Reservation.findByReservationNumber(RESERVATION_NUMBER)).willReturn(reservationMock);

        checkinValid.completePassengerCheckin();
    }

    @Test
    public void givenSelfCheckinAndValidDate_whenCompletingPassengerCheckin_shouldCompleteCheckin() {
        given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);
        willReturn(RESERVATION_NUMBER).given(passengerMock).getReservationNumber();
        given(Reservation.findByReservationNumber(RESERVATION_NUMBER)).willReturn(reservationMock);
        willReturn(VALID_FOR_CHECKIN_FLIGHT_DATE).given(reservationMock).getFlightDate();

        checkinValid.completePassengerCheckin();
    }

    @Test(expected = CheckinNotOnTimeException.class)
    public void givenSelfCheckinAndTooEarlyDate_whenIsValid_ShouldNotCompleteCheckin() {
        Checkin checkin = new Checkin(PASSENGER_HASH_WITH_RESERVATION, SELF_CHECKING);
        given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);
        willReturn(RESERVATION_NUMBER).given(passengerMock).getReservationNumber();
        given(Reservation.findByReservationNumber(RESERVATION_NUMBER)).willReturn(reservationMock);
        willReturn(TOO_EARLY_FOR_CHECKIN_FLIGHT_DATE).given(reservationMock).getFlightDate();

        checkin.completePassengerCheckin();
    }

    @Test(expected = CheckinNotOnTimeException.class)
    public void givenSelfCheckinAndTooLateDate_whenIsValid_shouldNotCompleteCheckin() {
        Checkin checkin = new Checkin(PASSENGER_HASH_WITH_RESERVATION, SELF_CHECKING);
        given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);
        willReturn(RESERVATION_NUMBER).given(passengerMock).getReservationNumber();
        given(Reservation.findByReservationNumber(RESERVATION_NUMBER)).willReturn(reservationMock);
        willReturn(TOO_LATE_FOR_CHECKIN_FLIGHT_DATE).given(reservationMock).getFlightDate();

        checkin.completePassengerCheckin();
    }

    @Test
    public void givenValidCheckin_whenCompletingCheckin_shouldCheckInPassenger() {
        given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);

        checkinValid.completePassengerCheckin();

        verify(passengerMock).checkin();
    }

    @Test
    public void givenValidCheckin_whenCompletingCheckin_shouldSavePassenger() {
        given(Passenger.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);

        checkinValid.completePassengerCheckin();

        verify(passengerMock).save();
    }
}
