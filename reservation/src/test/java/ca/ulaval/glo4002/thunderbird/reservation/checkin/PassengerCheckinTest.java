package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerAlreadyCheckedInException;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.ReservationNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Reservation.class})
public class PassengerCheckinTest {
    private static final String FIRST_NAME = "Uncle";
    private static final String LAST_NAME = "Bob";
    private static final String PASSPORT_NUMBER = "2564-5424";
    private static final String SEAT_CLASS = "economy";
    private static final int AGE = 18;
    private static final int NONEXISTENT_RESERVATION_NUMBER = 3;
    private static final int EXISTENT_RESERVATION_NUMBER = 5;
    private Passenger validPassenger;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Reservation.class);
        validPassenger = new Passenger(EXISTENT_RESERVATION_NUMBER, FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);

        given(Reservation.reservationExists(EXISTENT_RESERVATION_NUMBER)).willReturn(true);
        given(Reservation.reservationExists(NONEXISTENT_RESERVATION_NUMBER)).willReturn(false);
    }

    @Test
    public void givenValidPassengerForCheckin_whenIsValidForCheckin_shouldBeCheckedIn() {
        validPassenger.checkin();

        boolean passengerIsCheckedIn = validPassenger.isCheckedIn();
        assertTrue(passengerIsCheckedIn);
    }

    @Test(expected = MissingCheckinInformationException.class)
    public void givenPassengerWithoutFirstName_whenIsValidForCheckin_shouldThrowMissingCheckinInformationException() {
        Passenger passengerWithoutFirstName =
                new Passenger(EXISTENT_RESERVATION_NUMBER, "", LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);

        passengerWithoutFirstName.checkin();
    }

    @Test(expected = MissingCheckinInformationException.class)
    public void givenPassengerWithoutLastName_whenIsValidForCheckin_shouldThrowMissingCheckinInformationException() {
        Passenger passengerWithoutLastName =
                new Passenger(EXISTENT_RESERVATION_NUMBER, FIRST_NAME, "", AGE, PASSPORT_NUMBER, SEAT_CLASS);

        passengerWithoutLastName.checkin();
    }

    @Test(expected = MissingCheckinInformationException.class)
    public void givenPassengerWithoutPassportNumber_whenIsValidForCheckin_shouldThrowMissingCheckinInformationException() {
        Passenger passengerWithoutPassportNumber =
                new Passenger(EXISTENT_RESERVATION_NUMBER, FIRST_NAME, LAST_NAME, AGE, "", SEAT_CLASS);

        passengerWithoutPassportNumber.checkin();
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenPassengerWithNonExistentReservation_whenIsValidForCheckin_shouldThrowMissingCheckinInformationException() {
        Passenger passengerWithNonExistentReservationNumber =
                new Passenger(NONEXISTENT_RESERVATION_NUMBER, FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);

        passengerWithNonExistentReservationNumber.checkin();
    }

    @Test(expected = PassengerAlreadyCheckedInException.class)
    public void givenPassengerWithValidReservation_whenCheckingInTwoTimes_shouldThrowPassengerAlreadyCheckedInException() {
        given(Reservation.reservationExists(anyInt())).willReturn(true);

        validPassenger.checkin();
        validPassenger.checkin();
    }
}