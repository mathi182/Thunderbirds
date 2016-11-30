package ca.ulaval.glo4002.thunderbird.boarding.application.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

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
        PassengerRepository passengerRepository = createPassengerRepoMock(true);
        BaggageApplicationService applicationService = new BaggageApplicationService(passengerRepository);

        applicationService.registerBaggage(PASSENGER_HASH, baggage);
    }

    private PassengerRepository createPassengerRepoMock(boolean passengerAreCheckedIn) {
        willReturn(passengerAreCheckedIn).given(passenger).isCheckin();

        PassengerRepository passengerRepository = mock(PassengerRepository.class);
        willReturn(passenger).given(passengerRepository).getPassenger(PASSENGER_HASH);

        return passengerRepository;
    }
}