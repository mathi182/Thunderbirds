package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PassengerAssemblerTest {
    private static final UUID VALID_PASSENGER_HASH = UUID.randomUUID();
    private static final Seat.SeatClass ECONOMY_SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final Seat.SeatClass BUSINESS_SEAT_CLASS = Seat.SeatClass.BUSINESS;
    private static final String ECONOMY = "economy";
    private static final String BUSINESS = "business";
    private static final Instant VALID_FLIGHT_DATE = Instant.now();
    private static final String VALID_FLIGHT_NUMBER = "QK-918";

    @Test
    public void givenFilledPassengerRequest_whenTransformingToDomain_shouldBeTheCorrectPassenger(){
        PassengerDTO passengerDTO = new PassengerDTO(VALID_PASSENGER_HASH.toString(), ECONOMY, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER);

        Passenger actualPassenger = new PassengerAssembler().toDomain(passengerDTO);
        UUID actualPassengerHash = actualPassenger.getHash();
        boolean isTheSameSeatClass = actualPassenger.isSameSeatClass(ECONOMY_SEAT_CLASS);

        UUID expectedPassengerHash = VALID_PASSENGER_HASH;
        assertEquals(expectedPassengerHash,actualPassengerHash);
        assertTrue(isTheSameSeatClass);
    }

    @Test
    public void givenBusinessPassengerRequest_whenTransformingToDomain_shouldBeTheCorrectPassenger(){
        PassengerDTO passengerDTO = new PassengerDTO(VALID_PASSENGER_HASH.toString(), BUSINESS, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER);

        Passenger actualPassenger = new PassengerAssembler().toDomain(passengerDTO);
        UUID actualPassengerHash = actualPassenger.getHash();
        boolean isTheSameSeatClass = actualPassenger.isSameSeatClass(BUSINESS_SEAT_CLASS);

        UUID expectedPassengerHash = VALID_PASSENGER_HASH;
        assertEquals(expectedPassengerHash,actualPassengerHash);
        assertTrue(isTheSameSeatClass);
    }
}