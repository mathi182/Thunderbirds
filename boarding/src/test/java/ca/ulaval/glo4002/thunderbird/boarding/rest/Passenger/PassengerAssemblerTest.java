package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class PassengerAssemblerTest {
    private static final UUID VALID_PASSENGER_HASH = UUID.randomUUID();
    private static final Seat.SeatClass VALID_PASSENGER_SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final String ECONOMY = "economy";

    @Test
    public void givenFilledPassengerRequest_whenTransformingToDomain_ShouldBeTheCorrectPassenger(){
        PassengerRequest passengerRequest = new PassengerRequest(VALID_PASSENGER_HASH.toString(),ECONOMY);

        Passenger actualPassenger = new PassengerAssembler().toDomain(passengerRequest);
        UUID actualPassengerHash = actualPassenger.getHash();
        boolean isTheSameSeatClass = actualPassenger.isSameSeatClass(VALID_PASSENGER_SEAT_CLASS);

        UUID expectedPassengerHash = VALID_PASSENGER_HASH;
        assertEquals(expectedPassengerHash,actualPassengerHash);
        assertTrue(isTheSameSeatClass);
    }
}