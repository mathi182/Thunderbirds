package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PassengerTest {
    private static final UUID VALID_PASSENGER_HASH = UUID.randomUUID();
    private static final Seat.SeatClass VALID_PASSENGER_SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final Instant VALID_FLIGHT_DATE = Instant.ofEpochMilli(new Date().getTime());
    private static final String VALID_FLIGHT_NUMBER = "QK-918";

    private Passenger passenger;

    @Before
    public void setup(){
        passenger = new Passenger(VALID_PASSENGER_HASH, VALID_PASSENGER_SEAT_CLASS, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER);
    }

    @Test
    public void givenNewPassenger_whenGettingPassengerID_shouldReturnPassengerID(){
        UUID actualID = passenger.getHash();

        assertEquals(VALID_PASSENGER_HASH, actualID);
    }

    @Test
    public void givenNewPassenger_whenComparingSeatClass_shouldBeTheSameAsTheOneWeInputed(){
        boolean actualSeatClass = passenger.isSameSeatClass(VALID_PASSENGER_SEAT_CLASS);

        assertTrue(actualSeatClass);
    }

    @Test
    public void givenNewPassenger_whenGettingPassengerFlightDate_shouldReturnFlightDate() {
        Instant actualFlightDate = passenger.getFlightDate();

        assertEquals(VALID_FLIGHT_DATE, actualFlightDate);
    }

    @Test
    public void givenNewPassenger_whenGettingPassengerFlightNumber_shouldReturnPassengerFlightNumber() {
        String actualFlightNumber = passenger.getFlightNumber();

        assertEquals(VALID_FLIGHT_NUMBER, actualFlightNumber);
    }
}