package ca.ulaval.glo4002.thunderbird.boarding.application.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BaggageApplicationServiceTest {

    private static final UUID PASSENGER_HASH = UUID.randomUUID();
    private static final UUID BAGGAGE_ID = UUID.randomUUID();

    @Test
    public void givenCheckedInPassenger_whenRegisteringBaggage_shouldAddBaggageToPassenger() {
        Passenger passenger = mock(Passenger.class);
        PassengerRepository passengerRepository = mock(PassengerRepository.class);
        willReturn(passenger).given(passengerRepository).getPassenger(PASSENGER_HASH);
        Baggage baggage = mock(Baggage.class);
        willReturn(BAGGAGE_ID).given(baggage).getId();
        BaggageApplicationService applicationService = new BaggageApplicationService(passengerRepository);

        UUID actualResult = applicationService.registerBaggage(PASSENGER_HASH, baggage);

        verify(passenger).addBaggage(baggage);
        assertEquals(BAGGAGE_ID, actualResult);
    }
}