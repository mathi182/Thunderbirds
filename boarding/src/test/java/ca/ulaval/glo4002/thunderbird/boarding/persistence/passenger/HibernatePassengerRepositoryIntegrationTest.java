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
import org.junit.Ignore;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

public class HibernatePassengerRepositoryIntegrationTest {
    private static final String CHECKED = "checked";
    private static final UUID VALID_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION = UUID.randomUUID();
    private static final UUID NOT_CHECKED_IN_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID NOT_CHECKED_IN_ANYWHERE_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID NOT_CHECKED_IN_AND_NOT_SAVED_PASSENGER_UUID = UUID.randomUUID();
    private static final UUID CHECKED_IN_PASSENGER_UUID = UUID.randomUUID();
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
    private Passenger updatePassenger = mock(Passenger.class);

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
        Passenger expectedPassenger = new Passenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION,
                Seat.SeatClass.ECONOMY, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, IS_VIP, CHECKED_IN);
        willReturn(expectedPassenger).given(passengerService).fetchPassenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION);

        Passenger actualPassenger = repository.getPassenger(VALID_PASSENGER_UUID_PRESENT_IN_RESERVATION);

        assertEquals(expectedPassenger, actualPassenger);
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

    @Test
    public void givenNonCheckedInAndSavedPassenger_whenGettingPassenger_shouldCheckForUpdates(){
        Passenger expectedPassenger = new Passenger(NOT_CHECKED_IN_PASSENGER_UUID,
                Seat.SeatClass.ECONOMY, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, IS_VIP, NOT_CHECKED_IN);
        repository.savePassenger(expectedPassenger);
        willReturn(updatePassenger).given(passengerService).fetchPassenger(NOT_CHECKED_IN_PASSENGER_UUID);
        repository.getPassenger(NOT_CHECKED_IN_PASSENGER_UUID);

        verify(passengerService).fetchPassenger(NOT_CHECKED_IN_PASSENGER_UUID);
    }

    @Test
    public void givenNonCheckedInAndNotSavedPassenger_whenGettingPassenger_shouldCallServiceOnlyOnce(){
        Passenger expectedPassenger = new Passenger(NOT_CHECKED_IN_AND_NOT_SAVED_PASSENGER_UUID,
                Seat.SeatClass.ECONOMY, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, IS_VIP, NOT_CHECKED_IN);
        willReturn(expectedPassenger).given(passengerService).fetchPassenger(NOT_CHECKED_IN_AND_NOT_SAVED_PASSENGER_UUID);

        repository.getPassenger(NOT_CHECKED_IN_AND_NOT_SAVED_PASSENGER_UUID);

        verify(passengerService,times(1)).fetchPassenger(NOT_CHECKED_IN_AND_NOT_SAVED_PASSENGER_UUID);
    }

    @Test
    public void givenCheckedInAndSavedPassenger_whenGettingPassenger_shouldNotCheckForUpdates(){
        Passenger expectedPassenger = new Passenger(CHECKED_IN_PASSENGER_UUID,
                Seat.SeatClass.ECONOMY, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, IS_VIP, CHECKED_IN);
        repository.savePassenger(expectedPassenger);

        repository.getPassenger(CHECKED_IN_PASSENGER_UUID);

        verify(passengerService,never()).fetchPassenger(CHECKED_IN_PASSENGER_UUID);
    }

    @Test
    public void givenAPassengerCheckedInOnReservation_whenGettingPassenger_shouldCheckInPassenger(){
        UUID checkedInOnReservationPassengerHash = UUID.randomUUID();
        Passenger expectedPassenger = new Passenger(checkedInOnReservationPassengerHash,
                Seat.SeatClass.ECONOMY, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, IS_VIP, NOT_CHECKED_IN);
        repository.savePassenger(expectedPassenger);
        willReturn(true).given(updatePassenger).isCheckedIn();
        willReturn(updatePassenger).given(passengerService).fetchPassenger(checkedInOnReservationPassengerHash);

        Passenger actualPassenger = repository.getPassenger(checkedInOnReservationPassengerHash);

        verify(passengerService,times(1)).fetchPassenger(checkedInOnReservationPassengerHash);
        assertTrue(actualPassenger.isCheckedIn());
    }

    @Test
    public void givenAPassengerNotCheckedInAnywhere_whenGettingPassenger_shouldNotCheckIn(){
        Passenger expectedPassenger = new Passenger(NOT_CHECKED_IN_ANYWHERE_PASSENGER_UUID,
                Seat.SeatClass.ECONOMY, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, IS_VIP, NOT_CHECKED_IN);
        repository.savePassenger(expectedPassenger);
        willReturn(false).given(updatePassenger).isCheckedIn();
        willReturn(updatePassenger).given(passengerService).fetchPassenger(NOT_CHECKED_IN_ANYWHERE_PASSENGER_UUID);

        Passenger actualPassenger = repository.getPassenger(NOT_CHECKED_IN_ANYWHERE_PASSENGER_UUID);

        assertFalse(actualPassenger.isCheckedIn());
    }

    @Test
    public void givenAPassengerCheckedInOnReservation_whenGettingPassenger_shouldSavePassenger(){
        UUID checkedInOnReservationPassengerHash = UUID.randomUUID();
        Passenger expectedPassenger = new Passenger(checkedInOnReservationPassengerHash,
                Seat.SeatClass.ECONOMY, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, IS_VIP, NOT_CHECKED_IN);
        repository.savePassenger(expectedPassenger);
        willReturn(true).given(updatePassenger).isCheckedIn();
        willReturn(updatePassenger).given(passengerService).fetchPassenger(checkedInOnReservationPassengerHash);

        repository.getPassenger(checkedInOnReservationPassengerHash);
        Passenger actualPassenger = repository.getPassenger(checkedInOnReservationPassengerHash);

        verify(passengerService,times(1)).fetchPassenger(checkedInOnReservationPassengerHash);
        assertTrue(actualPassenger.isCheckedIn());
    }
}