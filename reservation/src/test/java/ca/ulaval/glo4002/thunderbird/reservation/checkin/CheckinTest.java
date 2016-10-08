package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerAssembly;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.util.DateLong;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.BDDMockito.given;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Reservation.class, PassengerAssembly.class,DateLong.class})
public class CheckinTest {
    private static final String PASSENGER_HASH = "passenger_hash";
    private static final int RESERVATION_NUMBER = 15;
    public static final String AGENT_ID = "agentId";
    public static final String SELF_CHECKING = "SELF";
    public static final String PASSENGER_HASH_WITH_RESERVATION = "passenger_hash_with_reservation";
    public static final String PASSENGER_HASH_WITH_INVALID_PASSENGER = "passenger_hash_with_invalid_passenger";
    private static final long TODAYS_DATE = 1473166800000L; // 2016-09-06T13:00
    private static final String VALID_FOR_CHECKIN_FLIGHT_DATE = "2016-09-06T21:00:00Z";
    private static final String TOO_LATE_FOR_CHECKIN_FLIGHT_DATE = "2016-09-06T14:00:00Z";
    private static final String TOO_EARLY_FOR_CHECKIN_FLIGHT_DATE = "2016-09-02T21:00:00Z";;

    private Checkin checkinValid;
    private Checkin checkinWithInvalidPassenger;
    private CheckinSelf checkinSelf;
    private PassengerAssembly passengerMock;
    private Reservation reservationMock;

    @Before
    public void setup() {
        mockStatic(Reservation.class);
        mockStatic(DateLong.class);
        mockStatic(Reservation.class);
        mockStatic(PassengerAssembly.class);
        mockStatic(DateLong.class);

        this.checkinValid = new Checkin(PASSENGER_HASH_WITH_RESERVATION, AGENT_ID);
        this.checkinWithInvalidPassenger = new Checkin(PASSENGER_HASH_WITH_INVALID_PASSENGER,AGENT_ID);
        this.checkinSelf = new CheckinSelf(PASSENGER_HASH_WITH_RESERVATION);

        passengerMock = mock(PassengerAssembly.class);
        reservationMock = mock(Reservation.class);

        given(DateLong.getLongCurrentDate()).willReturn(TODAYS_DATE);
    }

    @Test
    public void whenCreatingNewSelfCheckin_shouldBeSelfCheckin() {
        Checkin checkintest = new Checkin(PASSENGER_HASH,SELF_CHECKING);

        assertTrue(checkintest.isSelfCheckin());
    }

    @Test
    public void whenCreatingNewCheckin_shouldNotBeSelfCheckin() {
        Checkin checkintest = new Checkin(PASSENGER_HASH,AGENT_ID);

        assertFalse(checkintest.isSelfCheckin());
    }

    @Test
    public void givenCheckinValid_WhenPassengerExist_PassengerShouldExist() {
        given(PassengerAssembly.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);

        assertTrue(checkinValid.passengerExist());
    }

    @Test
    public void givenCheckinValid_WhenPassengerDoesNotExist_PassengerShouldNotExist() {
        given(PassengerAssembly.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION))
                .willThrow(new PassengerNotFoundException(PASSENGER_HASH_WITH_RESERVATION));

        assertFalse(checkinValid.passengerExist());
    }

    @Test
    public void givenCheckinValid_WhenIsValid_ShouldBeValid() {
        given(PassengerAssembly.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);
        willReturn(true).given(passengerMock).isValidForCheckin();

        assertTrue(checkinValid.isValid());
    }

    @Test
    public void givenCheckinWithInvalidPassenger_WhenIsValid_ShouldNotBeValid() {
        given(PassengerAssembly.findByPassengerHash(PASSENGER_HASH_WITH_INVALID_PASSENGER)).willReturn(passengerMock);
        willReturn(false).given(passengerMock).isValidForCheckin();

        assertFalse(checkinWithInvalidPassenger.isValid());
    }

    @Test
    public void givenSelfCheckinValid_whenIsValid_ShoulBeValid() {
        given(PassengerAssembly.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);
        willReturn(RESERVATION_NUMBER).given(passengerMock).getReservationNumber();
        given(Reservation.findByReservationNumber(RESERVATION_NUMBER)).willReturn(reservationMock);
        willReturn(VALID_FOR_CHECKIN_FLIGHT_DATE).given(reservationMock).getFlightDate();
        willReturn(true).given(passengerMock).isValidForCheckin();

        assertTrue(checkinSelf.isValid());
    }

    @Test
    public void givenSelfCheckinAndTooEarlyDate_whenIsValid_ShouldNotBeValid() {
        given(PassengerAssembly.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);
        willReturn(RESERVATION_NUMBER).given(passengerMock).getReservationNumber();
        given(Reservation.findByReservationNumber(RESERVATION_NUMBER)).willReturn(reservationMock);
        willReturn(TOO_EARLY_FOR_CHECKIN_FLIGHT_DATE).given(reservationMock).getFlightDate();
        willReturn(true).given(passengerMock).isValidForCheckin();

        assertFalse(checkinSelf.isValid());
    }

    @Test
    public void givenSelfCheckinAndTooLateDate_whenIsValid_shouldNotBeValid() {
        given(PassengerAssembly.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);
        willReturn(RESERVATION_NUMBER).given(passengerMock).getReservationNumber();
        given(Reservation.findByReservationNumber(RESERVATION_NUMBER)).willReturn(reservationMock);
        willReturn(TOO_LATE_FOR_CHECKIN_FLIGHT_DATE).given(reservationMock).getFlightDate();
        willReturn(true).given(passengerMock).isValidForCheckin();

        assertFalse(checkinSelf.isValid());
    }

    @Test
    public void givenSelfCheckinWithCorrectDateAndInvalidPassenger_whenIsValid_shouldNotBeValid() {
        given(PassengerAssembly.findByPassengerHash(PASSENGER_HASH_WITH_RESERVATION)).willReturn(passengerMock);
        willReturn(RESERVATION_NUMBER).given(passengerMock).getReservationNumber();
        given(Reservation.findByReservationNumber(RESERVATION_NUMBER)).willReturn(reservationMock);
        willReturn(VALID_FOR_CHECKIN_FLIGHT_DATE).given(reservationMock).getFlightDate();
        willReturn(false).given(passengerMock).isValidForCheckin();

        assertFalse(checkinSelf.isValid());
    }
}