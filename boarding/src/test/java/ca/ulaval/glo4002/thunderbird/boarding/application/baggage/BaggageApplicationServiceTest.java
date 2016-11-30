package ca.ulaval.glo4002.thunderbird.boarding.application.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BaggageApplicationServiceTest {

    public static final UUID PASSENGER_HASH = UUID.randomUUID();
    public static final UUID BAGGAGE_ID = UUID.randomUUID();
    private Baggage baggage;
    private Passenger passenger;

    @Before
    public void setUp() throws Exception {
        baggage = mock(Baggage.class);
        passenger = mock(Passenger.class);
        willReturn(BAGGAGE_ID).given(baggage).getId();
    }

    @Test
    public void givenCheckedInPassenger_whenRegisteringBaggage_shouldAddBaggageToPassenger() {
        PassengerRepository passengerRepository = createPassengerRepoMock();
        BaggageApplicationService applicationService = new BaggageApplicationService(passengerRepository);

        UUID actualResult = applicationService.registerBaggage(PASSENGER_HASH, baggage);

        verify(passenger).addBaggage(baggage);
        assertEquals(BAGGAGE_ID, actualResult);
    }

    private PassengerRepository createPassengerRepoMock() {
        PassengerRepository passengerRepository = mock(PassengerRepository.class);
        willReturn(passenger).given(passengerRepository).getPassenger(PASSENGER_HASH);

        return passengerRepository;
    }
}