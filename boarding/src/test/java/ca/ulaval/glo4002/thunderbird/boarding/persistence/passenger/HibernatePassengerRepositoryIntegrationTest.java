package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.CheckedBaggageEconomy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.exceptions.RepositorySavingException;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger.PassengerFetcher;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.LinearDimensionUnits.CM;
import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.WeightUnits.KG;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

public class HibernatePassengerRepositoryIntegrationTest {
    private static final UUID VALID_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION = UUID.randomUUID();
    private static final UUID NON_EXISTENT_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID PASSENGER_UUID_WITH_BAGGAGE = UUID.randomUUID();
    private PassengerRepository hibernatePassengerRepository;
    private PassengerFetcher passengerFetcher = mock(PassengerFetcher.class);

    @Before
    public void setup() {
        hibernatePassengerRepository = new HibernatePassengerRepositoryImpl(passengerFetcher);
    }

    @Test(expected = PassengerNotFoundException.class)
    public void givenEmptyRepository_whenGettingPassenger_shouldThrowException() {
        willThrow(new PassengerNotFoundException(NON_EXISTENT_PASSENGER_UUID))
                .given(passengerFetcher).fetchPassenger(NON_EXISTENT_PASSENGER_UUID);

        hibernatePassengerRepository.getPassenger(NON_EXISTENT_PASSENGER_UUID);
    }

    @Test
    public void givenEmptyRepository_whenSavingPassenger_shouldBeSavedCorrectly() throws RepositorySavingException {
        Passenger expectedPassenger = new Passenger(VALID_PASSENGER_UUID, Seat.SeatClass.ANY);

        hibernatePassengerRepository.savePassenger(expectedPassenger);
        Passenger actualPassenger = hibernatePassengerRepository.getPassenger(VALID_PASSENGER_UUID);

        assertEquals(expectedPassenger, actualPassenger);
    }

    @Test
    public void givenEmptyRepository_whenGettingAPassengerPresentInReservation_shouldReturnThePassenger() {
        Passenger expectedPassenger = new Passenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION,Seat.SeatClass.ANY);
        willReturn(expectedPassenger).given(passengerFetcher).fetchPassenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION);

        Passenger actualPassenger = hibernatePassengerRepository.getPassenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION);

        assertEquals(expectedPassenger,actualPassenger);
    }

    @Test
    public void givenEmptyRepository_whenSavingPassengerWithBaggages_shouldSaveBaggageCorrectly() throws RepositorySavingException {
        Baggage baggage = new CheckedBaggageEconomy(CM, 10, KG, 10);
        List<Baggage> baggageList = Arrays.asList(baggage);
        Passenger expectedPassenger = new Passenger(PASSENGER_UUID_WITH_BAGGAGE, Seat.SeatClass.ANY, baggageList);

        hibernatePassengerRepository.savePassenger(expectedPassenger);
        Passenger actualPassenger = hibernatePassengerRepository.getPassenger(PASSENGER_UUID_WITH_BAGGAGE);

        assertEquals(expectedPassenger, actualPassenger);
    }
}