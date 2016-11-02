package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.exceptions.MissingFieldException;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerAlreadyCheckedInException;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.exceptions.ReservationNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PassengerCheckinTest {

    private static final String FIRST_NAME = "Uncle";
    private static final String LAST_NAME = "Bob";
    private static final String PASSPORT_NUMBER = "2564-5424";
    private static final String SEAT_CLASS = "economy";
    private static final int AGE = 18;

    private Passenger validPassenger;
    private Reservation reservation;

    @Before
    public void setUp() {
        reservation = mock(Reservation.class);

        validPassenger = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        validPassenger.setReservation(reservation);
    }

    @Test
    public void givenValidPassengerForCheckin_whenWeCheckin_shouldBeCheckedIn() {
        validPassenger.checkin();

        boolean passengerIsCheckedIn = validPassenger.isCheckedIn();
        assertTrue(passengerIsCheckedIn);
    }

    @Test(expected = MissingFieldException.class)
    public void givenPassengerWithoutFirstName_whenWeCheckin_shouldThrowMissingCheckinInformationException() {
        Passenger passengerWithoutFirstName = new Passenger("", LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);
        passengerWithoutFirstName.setReservation(reservation);

        passengerWithoutFirstName.checkin();
    }

    @Test(expected = MissingFieldException.class)
    public void givenPassengerWithoutLastName_whenWeCheckin_shouldThrowMissingCheckinInformationException() {
        Passenger passengerWithoutLastName = new Passenger(FIRST_NAME, "", AGE, PASSPORT_NUMBER, SEAT_CLASS);
        passengerWithoutLastName.setReservation(reservation);

        passengerWithoutLastName.checkin();
    }

    @Test(expected = MissingFieldException.class)
    public void givenPassengerWithoutPassportNumber_whenWeCheckin_shouldThrowMissingCheckinInformationException() {
        Passenger passengerWithoutPassportNumber = new Passenger(FIRST_NAME, LAST_NAME, AGE, "", SEAT_CLASS);
        passengerWithoutPassportNumber.setReservation(reservation);

        passengerWithoutPassportNumber.checkin();
    }

    @Test(expected = ReservationNotFoundException.class)
    public void givenPassengerWithoutReservation_whenWeCheckin_shouldThrowReservationNotFoundException() {
        Passenger passengerWithoutReservation = new Passenger(FIRST_NAME, LAST_NAME, AGE, PASSPORT_NUMBER, SEAT_CLASS);

        passengerWithoutReservation.checkin();
    }

    @Test(expected = PassengerAlreadyCheckedInException.class)
    public void givenValidPassengerForCheckin_whenCheckinTwoTimes_shouldThrowPassengerAlreadyCheckedInException() {
        validPassenger.checkin();
        validPassenger.checkin();
    }

}