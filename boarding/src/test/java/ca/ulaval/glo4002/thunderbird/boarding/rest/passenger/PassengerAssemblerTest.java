package ca.ulaval.glo4002.thunderbird.boarding.rest.passenger;

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
    private static final String ANY = "any";

    private static final Instant VALID_FLIGHT_DATE = Instant.now();
    private static final String VALID_FLIGHT_NUMBER = "QK-918";
    private PassengerAssembler passengerAssembler;
    private PassengerDTO passengerDTO;
    private Passenger actualPassenger;

    @Test
    public void givenFilledPassengerRequest_whenTransformingToDomain_shouldBeTheCorrectPassenger() {
        passengerDTO = new PassengerDTO(VALID_PASSENGER_HASH.toString(),
                ECONOMY,
                VALID_FLIGHT_DATE.toString(),
                VALID_FLIGHT_NUMBER);

        passengerAssembler = new PassengerAssembler();
        actualPassenger = passengerAssembler.toDomain(passengerDTO);

        UUID actualPassengerHash = actualPassenger.getHash();
        Instant actualFlightDate = actualPassenger.getFlightDate();
        String actualFlightNumber = actualPassenger.getFlightNumber();
        boolean isTheSameSeatClass = actualPassenger.isSameSeatClass(ECONOMY_SEAT_CLASS);

        assertEquals(VALID_PASSENGER_HASH, actualPassengerHash);
        assertEquals(VALID_FLIGHT_DATE, actualFlightDate);
        assertEquals(VALID_FLIGHT_NUMBER, actualFlightNumber);
        assertTrue(isTheSameSeatClass);
    }

    @Test
    public void givenBusinessPassengerRequest_whenTransformingToDomain_shouldBeTheCorrectPassenger() {
        passengerDTO = new PassengerDTO(VALID_PASSENGER_HASH.toString(),
                BUSINESS,
                VALID_FLIGHT_DATE.toString(),
                VALID_FLIGHT_NUMBER);

        passengerAssembler = new PassengerAssembler();
        actualPassenger = passengerAssembler.toDomain(passengerDTO);
        UUID actualPassengerHash = actualPassenger.getHash();
        Instant actualFlightDate = actualPassenger.getFlightDate();
        String actualFlightNumber = actualPassenger.getFlightNumber();
        boolean isTheSameSeatClass = actualPassenger.isSameSeatClass(BUSINESS_SEAT_CLASS);

        assertEquals(VALID_PASSENGER_HASH, actualPassengerHash);
        assertEquals(VALID_FLIGHT_DATE, actualFlightDate);
        assertEquals(VALID_FLIGHT_NUMBER, actualFlightNumber);
        assertTrue(isTheSameSeatClass);
    }

    @Test
    public void givenAnySeatClassPassengerRequest_whenTransformingToDomain_shouldBeTheCorrectPassenger() {
        passengerDTO = new PassengerDTO(VALID_PASSENGER_HASH.toString(),
                ANY,
                VALID_FLIGHT_DATE.toString(),
                VALID_FLIGHT_NUMBER);
        passengerAssembler = new PassengerAssembler();
        actualPassenger = passengerAssembler.toDomain(passengerDTO);

        UUID actualPassengerHash = actualPassenger.getHash();
        Instant actualFlightDate = actualPassenger.getFlightDate();
        String actualFlightNumber = actualPassenger.getFlightNumber();
        boolean isTheSameSeatClass = actualPassenger.isSameSeatClass(BUSINESS_SEAT_CLASS);

        assertEquals(VALID_PASSENGER_HASH, actualPassengerHash);
        assertEquals(VALID_FLIGHT_DATE, actualFlightDate);
        assertEquals(VALID_FLIGHT_NUMBER, actualFlightNumber);
        assertTrue(isTheSameSeatClass);
    }
}