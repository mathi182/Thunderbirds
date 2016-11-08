package ca.ulaval.glo4002.thunderbird.reservation.reservation;

import ca.ulaval.glo4002.thunderbird.reservation.TestConfig;
import ca.ulaval.glo4002.thunderbird.reservation.passenger.Passenger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReservationTest {
    private Reservation reservation;

    @Before
    public void givenDefaultReservation() {
        reservation = TestConfig.getDefaultReservation();
    }

    @Test
    public void shouldReturnTheRightId() {
        assertEquals(TestConfig.RESERVATION_NUMBER, reservation.getId());
    }

    @Test
    public void shouldReturnTheRightFlightNumber() {
        assertEquals(TestConfig.FLIGHT_NUMBER, reservation.getFlightNumber());
    }

    @Test
    public void shouldReturnTheRightFlightDate() {
        assertEquals(TestConfig.FLIGHT_DATE, reservation.getFlightDate());
    }

    @Test
    public void shouldReturnAPassenger() {
        assertFalse(reservation.getPassengers().isEmpty());
    }

    @Test
    public void shouldBeTheSameAsPassengerReservation() {
        Passenger passenger = reservation.getPassengers().get(0);
        Reservation passengerReservation = passenger.getReservation();

        assertSame(reservation, passengerReservation);
    }
}