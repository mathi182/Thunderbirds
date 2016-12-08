package ca.ulaval.glo4002.thunderbird.boarding.application.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.Flight;
import ca.ulaval.glo4002.thunderbird.boarding.domain.flight.FlightRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class PassengerAssemblerTest {
    private static final UUID PASSENGER_HASH = UUID.randomUUID();
    private static final String ECONOMY = "economy";
    private static final String FLIGHT_NUMBER = "flight_number";
    private static final Instant FLIGHT_DATE = Instant.now();
    private static final boolean IS_VIP = true;
    private static final boolean IS_CHECKIN = true;
    private static final boolean IS_A_CHILD = false;

    private final Flight flight = mock(Flight.class);
    private final FlightRepository flightRepository = mock(FlightRepository.class);
    private final PassengerAssembler passengerAssembler = new PassengerAssembler(flightRepository);

    @Before
    public void setUp() {
        willReturn(flight).given(flightRepository).getFlight(FLIGHT_NUMBER, FLIGHT_DATE);
    }

    @Test
    public void givenPassengerDTO_whenConvertingToDomain_ShouldReturnCorrectPassenger() {
        PassengerDTO passengerDTO = new PassengerDTO(PASSENGER_HASH, ECONOMY, FLIGHT_DATE,
                FLIGHT_NUMBER, IS_VIP, IS_CHECKIN, IS_A_CHILD);

        Passenger actualPassenger = passengerAssembler.toDomain(passengerDTO);

        assertEquals(PASSENGER_HASH, actualPassenger.getHash());
        assertEquals(Seat.SeatClass.ECONOMY, actualPassenger.getSeatClass());
        assertEquals(IS_VIP, actualPassenger.isVip());
        assertEquals(IS_CHECKIN, actualPassenger.isCheckedIn());
        assertEquals(IS_A_CHILD,actualPassenger.isAChild());
        assertSame(flight, actualPassenger.getFlight());
    }
}