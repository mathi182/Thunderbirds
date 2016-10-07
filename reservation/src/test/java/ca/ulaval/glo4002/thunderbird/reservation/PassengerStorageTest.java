package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.passenger.PassengerStorage;
import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Reservation.class)

public class PassengerStorageTest {
    private static final String FIRST_NAME = "Uncle";
    private static final String LAST_NAME = "Bob";
    private static final String PASSPORT_NUMBER = "2564-5424";
    private static final String SEAT_CLASS = "economy";
    private static final int AGE = 18;

    private PassengerStorage newPassengerWithAllInformation;
    private PassengerStorage newPassengerWithoutFirstName;
    private PassengerStorage newPassengerWithoutLastName;
    private PassengerStorage newPassengerWithoutPassportNumber;
    private PassengerStorage newPassengerWithAllInformationExceptReservationNumber;
    private PassengerStorage passengerWithNonExistentReservationNumber;

    private static final int NONEXISTENT_RESERVATION_NUMBER = 3;
    private static final int EXISTENT_RESERVATION_NUMBER = 5;

    private Reservation reservationMock;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void newPassenger() {
        PowerMockito.mockStatic(Reservation.class);
        newPassengerWithAllInformationExceptReservationNumber = new PassengerStorage(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        newPassengerWithAllInformation = new PassengerStorage(EXISTENT_RESERVATION_NUMBER,FIRST_NAME,LAST_NAME,AGE,PASSPORT_NUMBER,SEAT_CLASS);
        newPassengerWithoutPassportNumber = new PassengerStorage(FIRST_NAME, LAST_NAME, AGE, "", SEAT_CLASS);
        newPassengerWithoutLastName = new PassengerStorage(FIRST_NAME, "", AGE, PASSPORT_NUMBER, SEAT_CLASS);
        newPassengerWithoutFirstName = new PassengerStorage("", LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        passengerWithNonExistentReservationNumber = new PassengerStorage(NONEXISTENT_RESERVATION_NUMBER,FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);

        reservationMock = mock(Reservation.class);
    }

    @Test
    public void givenValidPassengerForCheckin_whenIsValidForCheckin_shouldReturnTrue() throws Exception {
        BDDMockito.given(Reservation.findByReservationNumber(EXISTENT_RESERVATION_NUMBER)).willReturn(reservationMock);
        assertTrue(newPassengerWithAllInformation.isValidForCheckin());
    }

    @Test
    public void givenPassengerWithoutFirstName_whenIsValidForCheckin_shouldReturnFalse() throws Exception {
        assertFalse(newPassengerWithoutFirstName.isValidForCheckin());
    }

    @Test
    public void givenPassengerWithoutLastName_whenIsValidForCheckin_shouldReturnFalse() throws Exception {
        assertFalse(newPassengerWithoutLastName.isValidForCheckin());
    }

    @Test
    public void givenPassengerWithoutPassportNumber_whenIsValidForCheckin_shouldReturnFalse() throws Exception {
        assertFalse(newPassengerWithoutPassportNumber.isValidForCheckin());
    }

    @Test
    public void givenPassengerWithoutReservationNumber_whenIsValidForCheckin_shouldReturnFalse() throws Exception{
        assertFalse(newPassengerWithAllInformationExceptReservationNumber.isValidForCheckin());
    }


    @Test
    public void givenPassengerWithNonExistentReservation_whenIsValidForCheckin_ShouldReturnFalse() throws Exception {
        BDDMockito.given(Reservation.findByReservationNumber(NONEXISTENT_RESERVATION_NUMBER)).willThrow(new ReservationNotFoundException(NONEXISTENT_RESERVATION_NUMBER));
        assertFalse(passengerWithNonExistentReservationNumber.isValidForCheckin());
    }
}