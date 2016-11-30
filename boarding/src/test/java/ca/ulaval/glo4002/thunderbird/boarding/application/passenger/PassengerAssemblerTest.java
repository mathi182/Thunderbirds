package ca.ulaval.glo4002.thunderbird.boarding.application.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.*;

public class PassengerAssemblerTest {

    private static final UUID PASSENGER_HASH = UUID.randomUUID();
    private static final String ECONOMY = "economy";
    private static final String BUSINESS = "business";
    private static final String FLIGHT_NUMBER = "flight_number";
    private static final Instant FLIGHT_DATE = Instant.now();
    private static final boolean IS_VIP = true;
    private static final boolean IS_CHECKIN = true;

    @Test
    public void givenPassengerDTO_whenConvertingToDomain_ShouldReturnCorrectPassenger() throws Exception {
        PassengerDTO passengerDTO = new PassengerDTO(PASSENGER_HASH,
                ECONOMY,
                FLIGHT_DATE.toString(),
                FLIGHT_NUMBER,
                IS_VIP,
                IS_CHECKIN);

        PassengerAssembler passengerAssembler = new PassengerAssembler();
        Passenger actualPassenger = passengerAssembler.toDomain(passengerDTO);

        assertEquals(PASSENGER_HASH, actualPassenger.getHash());
        assertEquals(Seat.SeatClass.ECONOMY, actualPassenger.getSeatClass());
        assertEquals(FLIGHT_DATE, actualPassenger.getFlightDate());
        assertEquals(IS_VIP, actualPassenger.isVip());
        assertEquals(IS_CHECKIN, actualPassenger.isCheckedIn());
    }
}