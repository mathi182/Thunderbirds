package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.exceptions.PassengerNotFoundException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.UUID;

import static com.sun.jersey.api.client.ClientResponse.Status.NOT_FOUND;
import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

public class PassengerFetcherTest{
    private static final UUID VALID_PASSENGER_HASH = UUID.randomUUID();
    private static final UUID RANDOM_UUID = UUID.randomUUID();

    private PassengerRequest passengerRequestMock;
    private PassengerAssembler passengerAssemblerMock;
    private PassengerAPICaller passengerAPIcallerMock;
    private ClientResponse clientResponseMock;
    private Passenger passengerMock;


    private PassengerFetcher passengerFetcherTest;


    @Before
    public void setup(){
        passengerAssemblerMock = mock(PassengerAssembler.class);
        passengerRequestMock = mock(PassengerRequest.class);
        passengerAPIcallerMock = mock(PassengerAPICaller.class);
        clientResponseMock = mock(ClientResponse.class);

        willReturn(clientResponseMock).given(passengerAPIcallerMock).requestPassenger(anyString());
        willReturn(passengerRequestMock).given(clientResponseMock).getEntity(PassengerRequest.class);
        willReturn(passengerMock).given(passengerAssemblerMock).toDomain(passengerRequestMock);

        passengerFetcherTest = new PassengerFetcher(passengerAssemblerMock,passengerAPIcallerMock);
    }



    @Test
    public void givenNewPassengerFetcher_whenRequestingValidPassenger_shouldBeCorrectPassenger(){
        willReturn(OK.getStatusCode()).given(clientResponseMock).getStatus();

        Passenger passenger = passengerFetcherTest.fetchPassenger(VALID_PASSENGER_HASH);

        assertEquals(passengerMock,passenger);
    }

    @Test(expected = PassengerNotFoundException.class)
    public void givenNewPassengerFetcher_whenRequestingNonExistingPassenger_shouldThrowPassengerNotFound(){
        willReturn(NOT_FOUND.getStatusCode()).given(clientResponseMock).getStatus();

        passengerFetcherTest.fetchPassenger(RANDOM_UUID);
    }
}