package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import ca.ulaval.glo4002.thunderbird.reservation.TestConfig;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.exceptions.PassengerAlreadyCheckedInException;
import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PassengerTest {
    private Passenger passenger;

    @Before
    public void givenDefaultPassenger() {
        passenger = TestConfig.getDefaultPassenger();
    }

    @Test
    public void shouldReturnAnId() {
        assertNotNull(passenger.getId());
    }

    @Test
    public void shouldReturnTheRightSeatClass() {
        assertEquals(TestConfig.SEAT_CLASS, passenger.getSeatClass());
    }

    @Test
    public void shouldReturnNoReservation() {
        assertNull(passenger.getReservation());
    }

    @Test
    public void whenSettingReservation_shouldReturnThisReservation() {
        Reservation expectedReservation = TestConfig.getDefaultReservation();
        passenger.setReservation(expectedReservation);

        assertSame(expectedReservation, passenger.getReservation());
    }

    @Test
    public void shouldNotBeCheckedIn() {
        assertFalse(passenger.isCheckedIn());
    }

    @Test
    public void whenCheckin_shouldBeCheckedIn() {
        passenger.checkin();

        assertTrue(passenger.isCheckedIn());
    }

    @Test(expected = PassengerAlreadyCheckedInException.class)
    public void givenPassengerAlreadyCheckin_whenCheckin_shouldThrowAnException() {
        passenger.checkin();

        passenger.checkin();
    }

}