package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.Luggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.exceptions.RepositorySavingException;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerService;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

public class HibernatePassengerRepositoryIntegrationTest {
    private static final UUID VALID_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION = UUID.randomUUID();
    private static final UUID NON_EXISTENT_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID PASSENGER_UUID_WITH_LUGGAGE = UUID.randomUUID();
    private static final Instant VALID_FLIGHT_DATE = Instant.ofEpochMilli(new Date().getTime());
    private static final String VALID_FLIGHT_NUMBER = "QK-918";
    private static final int LINEAR_DIMENSION_IN_MM = 10;
    private static final int WEIGHT_IN_KGS = 10;
    public static final String CHECKED = "checked";
    private PassengerRepository hibernatePassengerRepository;
    private PassengerService passengerService = mock(PassengerService.class);

    @Before
    public void setup() {
        hibernatePassengerRepository = new HibernatePassengerRepository(passengerService);
    }

    @Test(expected = PassengerNotFoundException.class)
    public void givenEmptyRepository_whenGettingPassenger_shouldThrowException() {
        willThrow(new PassengerNotFoundException(NON_EXISTENT_PASSENGER_UUID))
                .given(passengerService).fetchPassenger(NON_EXISTENT_PASSENGER_UUID);

        hibernatePassengerRepository.getPassenger(NON_EXISTENT_PASSENGER_UUID);
    }

    @Test
    public void givenEmptyRepository_whenSavingPassenger_shouldBeSavedCorrectly() throws RepositorySavingException {
        Passenger expectedPassenger = new Passenger(VALID_PASSENGER_UUID,
                                                    Seat.SeatClass.ANY,
                                                    VALID_FLIGHT_DATE,
                                                    VALID_FLIGHT_NUMBER);

        hibernatePassengerRepository.savePassenger(expectedPassenger);
        Passenger actualPassenger = hibernatePassengerRepository.getPassenger(VALID_PASSENGER_UUID);

        assertEquals(expectedPassenger, actualPassenger);
    }

    @Test
    public void givenEmptyRepository_whenGettingAPassengerPresentInReservation_shouldReturnThePassenger() {
        Passenger expectedPassenger = new Passenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION,
                                                    Seat.SeatClass.ANY,
                                                    VALID_FLIGHT_DATE,
                                                    VALID_FLIGHT_NUMBER);
        willReturn(expectedPassenger).given(passengerService).fetchPassenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION);

        Passenger actualPassenger = hibernatePassengerRepository.getPassenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION);

        assertEquals(expectedPassenger, actualPassenger);
    }

    @Test
    public void givenEmptyRepository_whenSavingPassengerWithLuggages_shouldSaveLuggagesCorrectly() throws RepositorySavingException {
        Luggage luggage = new Luggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_KGS, CHECKED);
        List<Luggage> luggageList = new ArrayList<>();
        luggageList.add(luggage);
        Passenger expectedPassenger = new Passenger(PASSENGER_UUID_WITH_LUGGAGE,
                                                    Seat.SeatClass.ANY,
                                                    VALID_FLIGHT_DATE,
                                                    VALID_FLIGHT_NUMBER,
                luggageList);

        hibernatePassengerRepository.savePassenger(expectedPassenger);
        Passenger actualPassenger = hibernatePassengerRepository.getPassenger(PASSENGER_UUID_WITH_LUGGAGE);

        assertEquals(expectedPassenger, actualPassenger);
    }
}