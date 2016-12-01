package ca.ulaval.glo4002.thunderbird.boarding.rest.Checkin;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.core.Response;
import java.net.ResponseCache;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.verify;
import static javax.ws.rs.core.Response.Status.OK;


public class CheckinRessourceTest {
    private static final UUID RANDOM_UUID = UUID.randomUUID();
    private static final UUID VALID_PASSENGER_UUID = UUID.randomUUID();

    private PassengerRepository repository;
    private Passenger passenger;

    private  CheckinRessource checkinRessource;

    @Before
    public void setup(){
        repository = mock(HibernatePassengerRepository.class);
        passenger = mock(Passenger.class);

        checkinRessource = new CheckinRessource(repository);
    }

    @Test(expected = PassengerNotFoundException.class)
    public void givenRandomUUID_whenCheckingInPassenger_shouldThrowPassengerNotFound(){
        willThrow(PassengerNotFoundException.class).given(repository).getPassenger(RANDOM_UUID);

        checkinRessource.checkinPassenger(RANDOM_UUID);
    }

    @Test
    public void givenPassengerUUID_whenCheckingInPassenger_shouldCheckinPassenger(){
        willReturn(passenger).given(repository).getPassenger(VALID_PASSENGER_UUID);

        checkinRessource.checkinPassenger(VALID_PASSENGER_UUID);

        verify(passenger).checkIn();
    }

    @Test
    public void givenPassengerUUID_whenCheckinInPassenger_shouldSavePassenger(){
        willReturn(passenger).given(repository).getPassenger(VALID_PASSENGER_UUID);

        checkinRessource.checkinPassenger(VALID_PASSENGER_UUID);

        verify(repository).savePassenger(passenger);
    }

    @Test
    public void givenPassengerUUID_whenCheckingInPassenger_shouldReturnOK(){
        willReturn(passenger).given(repository).getPassenger(VALID_PASSENGER_UUID);

        Response response = checkinRessource.checkinPassenger(VALID_PASSENGER_UUID);

        assertEquals(OK.getStatusCode(),response.getStatus());
    }

}