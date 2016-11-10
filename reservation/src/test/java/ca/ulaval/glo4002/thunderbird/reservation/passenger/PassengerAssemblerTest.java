package ca.ulaval.glo4002.thunderbird.reservation.passenger;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class PassengerAssemblerTest {
    private static final UUID PASSENGER_HASH = UUID.randomUUID();
    private static final String SEAT_CLASS = "economy";
    private static final String FLIGHT_NUMBER = "flight_number";
    private static final Instant FLIGHT_DATE = Instant.now();

    private PassengerAssembler passengerAssembler;
    private Passenger passengerMock;

    @Before
    public void setup(){
        passengerMock = mock(Passenger.class);
        willReturn(PASSENGER_HASH).given(passengerMock).getId();
        willReturn(SEAT_CLASS).given(passengerMock).getSeatClass();
        willReturn(FLIGHT_NUMBER).given(passengerMock).getFlightNumber();
        willReturn(FLIGHT_DATE).given(passengerMock).getFlightDate();

        passengerAssembler = new PassengerAssembler();
    }

    @Test
    public void givenNewPassenger_whenConvertingToDTO_ShouldGetCorrectPassengerHash(){
        PassengerDTO  passengerDTO = passengerAssembler.toDTO(passengerMock);
        String actualValue = passengerDTO.passengerHash;

        String expectedValue = PASSENGER_HASH.toString();
        assertEquals(expectedValue,actualValue);
    }

    @Test
    public void givenNewPassenger_whenConvertingToDTOShouldReturnCorrectValues(){
        PassengerDTO  passengerDTO = passengerAssembler.toDTO(passengerMock);
        String actualPassengerHash = passengerDTO.passengerHash;
        String actualSeatClass = passengerDTO.seatClass;
        String actualFlightNumber = passengerDTO.flightNumber;
        String actualFlighDate = passengerDTO.flightDate;

        assertEquals(PASSENGER_HASH.toString(),actualPassengerHash);
        assertEquals(SEAT_CLASS,actualSeatClass);
        assertEquals(FLIGHT_NUMBER,actualFlightNumber);
        assertEquals(FLIGHT_DATE.toString(),actualFlighDate);
    }
}