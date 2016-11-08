package ca.ulaval.glo4002.thunderbird.boarding.rest.Passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;


import java.util.UUID;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


public class PassengerFetcherTest{
    private static final String MOCK_URL = PassengerFetcher.URL;
    private static final UUID VALID_PASSENGER_HASH = UUID.randomUUID();
    private static final String PASSENGER_SERVICE_PATH = PassengerFetcher.SERVICE_PATH_FORMAT;

    private MockRestServiceServer mockServer;
    @Mock
    private RestTemplate mockRestTemplate;

    private PassengerFetcher passengerFetcherTest;

    @Before
    public void setup(){
        passengerFetcherTest = new PassengerFetcher();

        mockRestTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(mockRestTemplate);

    }

    @Test
    @Ignore //TODO mock a client response correctly
    public void givenNewPassengerFetcher_whenRequestingPassenger_ShouldCallAPI(){
        mockServer.expect(requestTo(MOCK_URL + PASSENGER_SERVICE_PATH + VALID_PASSENGER_HASH.toString()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{ \"first_name\" : \"Alexis\"," +
                        " \"last_name\" : \"Lessard\"," +
                        " \"passport_number\" : \"test\"" +
                        "\"seat_class\" : \"economy\"" +
                        "\"passenger_hash\" : \""+VALID_PASSENGER_HASH +"\"" +
                        "\"child\" : false }", MediaType.APPLICATION_JSON));
        passengerFetcherTest.fetchPassenger(VALID_PASSENGER_HASH);

        mockServer.verify();
    }
}