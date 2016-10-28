package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.checkin.MissingCheckinInformationException;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.util.DateLong;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Reservation.class, DateLong.class})

public class PassengerTest {
    private static final String FIRST_NAME = "Uncle";
    private static final String LAST_NAME = "Bob";
    private static final String PASSPORT_NUMBER = "2564-5424";
    private static final String SEAT_CLASS = "economy";
    private static final int AGE = 18;
    private static final int NONEXISTENT_RESERVATION_NUMBER = 3;
    private static final int EXISTENT_RESERVATION_NUMBER = 5;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private Passenger newPassengerWithoutFirstName;
    private Passenger newPassengerWithoutLastName;
    private Passenger newPassengerWithoutPassportNumber;
    private Passenger passengerWithNonExistentReservationNumber;
    private Passenger newPassengerWithAllInformation;

    @Before
    public void newPassenger() {
        PowerMockito.mockStatic(Reservation.class);
        PowerMockito.mockStatic(DateLong.class);

        newPassengerWithAllInformation =
                new Passenger(EXISTENT_RESERVATION_NUMBER, FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER,
                        SEAT_CLASS);

        newPassengerWithoutPassportNumber =
                new Passenger(FIRST_NAME, LAST_NAME, AGE, "", SEAT_CLASS);

        newPassengerWithoutLastName =
                new Passenger(FIRST_NAME, "", AGE, PASSPORT_NUMBER, SEAT_CLASS);

        newPassengerWithoutFirstName =
                new Passenger("", LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);

        passengerWithNonExistentReservationNumber =
                new Passenger(NONEXISTENT_RESERVATION_NUMBER, FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER,
                        SEAT_CLASS);

        newPassengerWithAllInformation =
                new Passenger(EXISTENT_RESERVATION_NUMBER, FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER,
                        SEAT_CLASS);

        newPassengerWithoutPassportNumber =
                new Passenger(FIRST_NAME, LAST_NAME, AGE, "", SEAT_CLASS);

        newPassengerWithoutLastName =
                new Passenger(FIRST_NAME, "", AGE, PASSPORT_NUMBER, SEAT_CLASS);

        newPassengerWithoutFirstName =
                new Passenger("", LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);

        passengerWithNonExistentReservationNumber =
                new Passenger(NONEXISTENT_RESERVATION_NUMBER, FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER,
                        SEAT_CLASS);
    }

    @Test
    public void givenValidPassengerForCheckin_whenIsValidForCheckin_shouldBeCheckedIn() {
        given(Reservation.reservationExists(EXISTENT_RESERVATION_NUMBER)).willReturn(true);

        newPassengerWithAllInformation.checkin();

        boolean passengerIsCheckedIn = newPassengerWithAllInformation.isCheckedIn();
        assertTrue(passengerIsCheckedIn);
    }

    @Test(expected = MissingCheckinInformationException.class)
    public void givenPassengerWithoutFirstName_whenIsValidForCheckin_shouldThrowMissingCheckinInformationException() {
        given(Reservation.reservationExists(anyInt())).willReturn(true);

        newPassengerWithoutFirstName.checkin();
    }

    @Test(expected = MissingCheckinInformationException.class)
    public void givenPassengerWithoutLastName_whenIsValidForCheckin_shouldThrowMissingCheckinInformationException() {
        given(Reservation.reservationExists(anyInt())).willReturn(true);

        newPassengerWithoutLastName.checkin();
    }

    @Test(expected = MissingCheckinInformationException.class)
    public void givenPassengerWithoutPassportNumber_whenIsValidForCheckin_shouldThrowMissingCheckinInformationException() {
        given(Reservation.reservationExists(anyInt())).willReturn(true);

        newPassengerWithoutPassportNumber.checkin();
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenPassengerWithNonExistentReservation_whenIsValidForCheckin_shouldThrowMissingCheckinInformationException() {
        given(Reservation.findByReservationNumber(anyInt()))
                .willThrow(new ReservationNotFoundException(NONEXISTENT_RESERVATION_NUMBER));

        passengerWithNonExistentReservationNumber.checkin();
    }

    @Test
    public void givenPassengerWithValidReservation_whenCheckingIn_shoudNotThrowExceptions() {
        given(Reservation.reservationExists(anyInt())).willReturn(true);

        newPassengerWithAllInformation.checkin();
    }

    @Test(expected = PassengerAlreadyCheckedInException.class)
    public void givenPassengerWithValidReservation_whenCheckingInTwoTimes_shouldThrowPassengerAlreadyCheckedInException() {
        given(Reservation.reservationExists(anyInt())).willReturn(true);

        newPassengerWithAllInformation.checkin();

        newPassengerWithAllInformation.checkin();
    }
}