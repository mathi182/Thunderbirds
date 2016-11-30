package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.exceptions.PassengerNotCheckedInException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.boarding.application.passenger.PassengerService;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Length;
import ca.ulaval.glo4002.thunderbird.boarding.util.units.Mass;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

public class HibernatePassengerRepositoryIntegrationTest {
    private static final String CHECKED = "checked";
    private static final UUID VALID_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION = UUID.randomUUID();
    private static final UUID NON_EXISTENT_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID PASSENGER_UUID_WITH_BAGGAGE = UUID.randomUUID();
    private static final Instant VALID_FLIGHT_DATE = Instant.ofEpochMilli(new Date().getTime());
    private static final String VALID_FLIGHT_NUMBER = "QK-918";
    private static final boolean IS_VIP = true;
    private static final Length LINEAR_DIMENSION_IN_MM = Length.fromMillimeters(10);
    private static final Mass WEIGHT_IN_KGS = Mass.fromKilograms(10);
    private static final boolean CHECKED_IN = true;
    private static final boolean NOT_CHECKED_IN = false;

    private PassengerService passengerService = mock(PassengerService.class);
    private PassengerRepository repository = new HibernatePassengerRepository(passengerService);

    @Test(expected = PassengerNotCheckedInException.class)
    public void givenPassengerNotCheckedIn_whenGettingCheckedInPassenger_shouldThrowException() {
        Passenger expectedPassenger = new Passenger(VALID_PASSENGER_UUID,
                Seat.SeatClass.ECONOMY, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, IS_VIP, NOT_CHECKED_IN);
        willReturn(expectedPassenger).given(passengerService).fetchPassenger(NON_EXISTENT_PASSENGER_UUID);

        repository.getCheckedInPassenger(NON_EXISTENT_PASSENGER_UUID);
    }

    @Test
    public void whenSavingPassenger_shouldBeSavedCorrectly() {
        Passenger expectedPassenger = new Passenger(VALID_PASSENGER_UUID,
                Seat.SeatClass.ECONOMY, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, IS_VIP, CHECKED_IN);

        repository.savePassenger(expectedPassenger);

        Passenger actualPassenger = repository.getPassenger(VALID_PASSENGER_UUID);
        assertEquals(expectedPassenger.getHash(), actualPassenger.getHash());
    }

    @Test
    public void givenAPassengerPresentInReservation_whenGettingPassenger_shouldReturnThePassenger() {
        Passenger expectedPassenger = givenACheckedInPassengerInReservation();

        Passenger actualPassenger = repository.getPassenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION);

        assertEquals(expectedPassenger, actualPassenger);
    }

    @Test
    public void givenACheckedInPassengerInReservation_whenGettingCheckedInPassenger_shouldReturnThePassenger() {
        Passenger expectedPassenger = givenACheckedInPassengerInReservation();

        Passenger actualPassenger = repository.getCheckedInPassenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION);

        assertEquals(expectedPassenger, actualPassenger);
    }

    private Passenger givenACheckedInPassengerInReservation() {
        Passenger passenger = new Passenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION,
                Seat.SeatClass.ECONOMY, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, IS_VIP, CHECKED_IN);
        willReturn(passenger).given(passengerService).fetchPassenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION);
        return passenger;
    }

    @Test
    public void givenPassengerWithBaggage_whenSavingThisPassenger_shouldSaveBaggagesCorrectly() {
        Passenger expectedPassenger = new Passenger(PASSENGER_UUID_WITH_BAGGAGE,
                Seat.SeatClass.ECONOMY, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, IS_VIP, CHECKED_IN);
        Baggage baggage = new Baggage(LINEAR_DIMENSION_IN_MM, WEIGHT_IN_KGS, CHECKED);
        expectedPassenger.addBaggage(baggage);

        repository.savePassenger(expectedPassenger);

        Passenger actualPassenger = repository.getPassenger(PASSENGER_UUID_WITH_BAGGAGE);
        assertFalse(actualPassenger.getBaggages().isEmpty());
    }
}