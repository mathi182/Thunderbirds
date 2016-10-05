package ca.ulaval.glo4002.thunderbird.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.exception.ReservationNotFoundException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PassengerTest {
    private static final String FIRST_NAME = "Uncle";
    private static final String LAST_NAME = "Bob";
    private static final String PASSPORT_NUMBER = "2564-5424";
    private static final String SEAT_CLASS = "economy";
    private static final int AGE = 18;
    private static final int NONEXISTENT_RESERVATION_NUMBER = 3;
    private static final int EXISTENT_RESERVATION_NUMBER = 5;
    private Passenger newPassengerWithAllInformationExceptReservationNumber;
    private Passenger newPassengerWithoutFirstName;
    private Passenger newPassengerWithoutLastName;
    private Passenger newPassengerWithoutPassportNumber;
    private Passenger passengerWithInvalidReservationNumber;
    private Passenger newPassengerWithAllInformation;


    @Before
    public void newPassenger() {
        newPassengerWithAllInformationExceptReservationNumber = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        newPassengerWithAllInformation = new Passenger(EXISTENT_RESERVATION_NUMBER,FIRST_NAME,LAST_NAME,AGE,PASSPORT_NUMBER,SEAT_CLASS);
        newPassengerWithoutPassportNumber = new Passenger(FIRST_NAME, LAST_NAME, AGE, "", SEAT_CLASS);
        newPassengerWithoutLastName = new Passenger(FIRST_NAME, "", AGE, PASSPORT_NUMBER, SEAT_CLASS);
        newPassengerWithoutFirstName = new Passenger("", LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        passengerWithInvalidReservationNumber = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);

        passengerWithInvalidReservationNumber.setReservationNumber(NONEXISTENT_RESERVATION_NUMBER);



    }

    @Test
    public void givenValidPassengerForCheckin_whenIsValidForCheckin_shouldReturnTrue() throws Exception {
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

    /*
    @Test
    public void givenPassengerWithNonExistentReservation_whenIsValidForCheckin_ShouldReturnFalse() throws Exception {
        assertFalse(passengerWithInvalidReservationNumber.isValidForCheckin());
    }
    */
}