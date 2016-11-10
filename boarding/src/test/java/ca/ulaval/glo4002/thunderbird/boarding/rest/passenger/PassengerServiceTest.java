package ca.ulaval.glo4002.thunderbird.boarding.rest.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerAssembler;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerDTO;
import ca.ulaval.glo4002.thunderbird.boarding.rest.passenger.PassengerService;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

import static com.sun.jersey.api.client.ClientResponse.Status.NOT_FOUND;
import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

//TODO On pense que c'est des tests d'integration car ils ont besoin de se
//se connecter au serveur.
public class PassengerServiceTest {
    private static final UUID VALID_PASSENGER_HASH = UUID.randomUUID();
    private static final UUID RANDOM_UUID = UUID.randomUUID();

    private PassengerDTO passengerDTOMock;
    private PassengerAssembler passengerAssemblerMock;
    private ClientResponse clientResponseMock;
    private Passenger passengerMock;

    private PassengerService passengerServiceTest;

    @Before
    public void setup() {
        passengerAssemblerMock = mock(PassengerAssembler.class);
        passengerDTOMock = mock(PassengerDTO.class);
        clientResponseMock = mock(ClientResponse.class);
        passengerMock = mock(Passenger.class);

        willReturn(passengerDTOMock).given(clientResponseMock).getEntity(PassengerDTO.class);
        willReturn(passengerMock).given(passengerAssemblerMock).toDomain(passengerDTOMock);

        passengerServiceTest = new PassengerService(passengerAssemblerMock);
    }

    @Test(expected = PassengerNotFoundException.class)
    public void givenNewPassengerService_whenRequestingNonExistingPassenger_shouldThrowPassengerNotFound() {
        willReturn(NOT_FOUND.getStatusCode()).given(clientResponseMock).getStatus();

        passengerServiceTest.fetchPassenger(RANDOM_UUID);
    }

    //TODO Test d'intégration
    @Test
    @Ignore
    public void givenNewPassengerService_whenRequestingValidPassenger_shouldBeCorrectPassenger(){
        willReturn(OK.getStatusCode()).given(clientResponseMock).getStatus();

        Passenger passenger = passengerServiceTest.fetchPassenger(VALID_PASSENGER_HASH);

        assertEquals(passengerMock,passenger);
    }
}