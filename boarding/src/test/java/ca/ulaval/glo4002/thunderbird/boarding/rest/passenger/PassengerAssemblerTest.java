package ca.ulaval.glo4002.thunderbird.boarding.rest.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat.SeatClass;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerAssembler.BUSINESS;
import static ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerAssembler.ECONOMY;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PassengerAssemblerTest {
    private static final UUID VALID_PASSENGER_HASH = UUID.randomUUID();
    private static final String ANY = "any";
    private static final String FLIGHT_DATE = "2016-09-06T13:00:00Z";
    private static final String FLIGHT_NUMBER = "QK-918";
    private static final boolean VIP = true;

    private PassengerAssembler passengerAssembler;

    @Before
    public void setUp() {
        passengerAssembler = new PassengerAssembler();
    }

    @Test
    public void givenFilledPassengerRequest_whenTransformingToDomain_shouldBeTheCorrectPassenger() {
        PassengerDTO passengerDTO = new PassengerDTO(VALID_PASSENGER_HASH, ANY, FLIGHT_DATE, FLIGHT_NUMBER, VIP);

        Passenger actualPassenger = passengerAssembler.toDomain(passengerDTO);

        UUID actualPassengerHash = actualPassenger.getHash();
        String actualFlightDate = ISO_INSTANT.format(actualPassenger.getFlightDate());
        String actualFlightNumber = actualPassenger.getFlightNumber();
        boolean isTheSameSeatClass = actualPassenger.isSameSeatClass(SeatClass.ANY);
        boolean isVip = actualPassenger.isVip();
        assertEquals(VALID_PASSENGER_HASH, actualPassengerHash);
        assertEquals(FLIGHT_DATE, actualFlightDate);
        assertEquals(FLIGHT_NUMBER, actualFlightNumber);
        assertTrue(isTheSameSeatClass);
        assertEquals(VIP, isVip);
    }

    @Test
    public void givenEconomyPassengerRequest_whenTransformingToDomain_shouldBeTheCorrectSeatClass() {
        PassengerDTO passengerDTO = new PassengerDTO(VALID_PASSENGER_HASH, ECONOMY, FLIGHT_DATE, FLIGHT_NUMBER, VIP);

        Passenger actualPassenger = passengerAssembler.toDomain(passengerDTO);

        boolean isTheSameSeatClass = actualPassenger.isSameSeatClass(SeatClass.ECONOMY);
        assertTrue(isTheSameSeatClass);
    }

    @Test
    public void givenBusinessPassengerRequest_whenTransformingToDomain_shouldBeTheCorrectSeatClass() {
        PassengerDTO passengerDTO = new PassengerDTO(VALID_PASSENGER_HASH, BUSINESS, FLIGHT_DATE, FLIGHT_NUMBER, VIP);

        Passenger actualPassenger = passengerAssembler.toDomain(passengerDTO);

        boolean isTheSameSeatClass = actualPassenger.isSameSeatClass(SeatClass.BUSINESS);
        assertTrue(isTheSameSeatClass);
    }
}