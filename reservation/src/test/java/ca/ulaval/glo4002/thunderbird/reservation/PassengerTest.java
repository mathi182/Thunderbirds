package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.exception.PassengerAlreadyCheckedInException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;

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
    private static final long TODAYS_DATE = 1473166800000L; // 2016-09-06T13:00
    private static final String VALID_FOR_CHECKIN_FLIGHT_DATE = "2016-09-06T21:00:00Z";

    private Passenger newPassengerWithAllInformationExceptReservationNumber;
    private Passenger newPassengerWithoutFirstName;
    private Passenger newPassengerWithoutLastName;
    private Passenger newPassengerWithoutPassportNumber;
    private Passenger passengerWithNonExistentReservationNumber;
    private Passenger newPassengerWithAllInformation;
    private Reservation reservationMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void newPassenger() {
        PowerMockito.mockStatic(Reservation.class);
        PowerMockito.mockStatic(DateLong.class);
        newPassengerWithAllInformationExceptReservationNumber =
                new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);

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

        newPassengerWithAllInformationExceptReservationNumber =
                new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);

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

        reservationMock = mock(Reservation.class);
        given(DateLong.getLongCurrentDate()).willReturn(TODAYS_DATE);
    }

    @Test
    public void givenValidPassengerForCheckin_whenIsValidForCheckin_shouldReturnTrue() {
        given(Reservation.findByReservationNumber(EXISTENT_RESERVATION_NUMBER)).willReturn(reservationMock);

        boolean isValidForCheckin = newPassengerWithAllInformation.isValidForCheckin();

        assertTrue(isValidForCheckin);
    }

    @Test
    public void givenPassengerWithoutFirstName_whenIsValidForCheckin_shouldReturnFalse() {
        boolean isValidForCheckin = newPassengerWithoutFirstName.isValidForCheckin();

        assertFalse(isValidForCheckin);
    }

    @Test
    public void givenPassengerWithoutLastName_whenIsValidForCheckin_shouldReturnFalse() {
        boolean isValidForCheckin = newPassengerWithoutLastName.isValidForCheckin();

        assertFalse(isValidForCheckin);
    }

    @Test
    public void givenPassengerWithoutPassportNumber_whenIsValidForCheckin_shouldReturnFalse() {
        boolean isValidForCheckin = newPassengerWithoutPassportNumber.isValidForCheckin();

        assertFalse(isValidForCheckin);
    }

    @Test
    public void givenPassengerWithoutReservationNumber_whenIsValidForCheckin_shouldReturnFalse() {
        boolean isValidForCheckin = newPassengerWithAllInformationExceptReservationNumber.isValidForCheckin();

        assertFalse(isValidForCheckin);
    }

    @Test
    public void givenPassengerWithNonExistentReservation_whenIsValidForCheckin_shouldReturnFalse() {
        given(Reservation.findByReservationNumber(NONEXISTENT_RESERVATION_NUMBER))
                .willThrow(new ReservationNotFoundException(NONEXISTENT_RESERVATION_NUMBER));

        boolean isValidForCheckin = passengerWithNonExistentReservationNumber.isValidForCheckin();

        assertFalse(isValidForCheckin);
    }

    @Test
    public void givenPassengerWithValidReservation_whenCheckingIn_shoudNotThrowExceptions() {
        newPassengerWithAllInformation.checkin();
    }

    @Test(expected = PassengerAlreadyCheckedInException.class)
    public void givenPassengerWithValidReservation_whenCheckingInTwoTimes_shouldThrowPassengerAlreadyCheckedInException(){
        newPassengerWithAllInformation.checkin();

        newPassengerWithAllInformation.checkin();
    }
}