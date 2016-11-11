package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.BaggageAmountAuthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class PassengerTest {
    private static final UUID VALID_PASSENGER_HASH = UUID.randomUUID();
    private static final Seat.SeatClass VALID_PASSENGER_SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final Instant VALID_FLIGHT_DATE = Instant.ofEpochMilli(new Date().getTime());
    private static final String VALID_FLIGHT_NUMBER = "QK-918";

    private Passenger passengerWithoutBaggage;
    private Passenger passengerWithMaximalBaggageAmountAuthorized;
    private Baggage baggageMock;

    @Before
    public void setup(){
        passengerWithoutBaggage = new Passenger(VALID_PASSENGER_HASH, VALID_PASSENGER_SEAT_CLASS, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER);
        baggageMock = mock(Baggage.class);

        List<Baggage> baggagesCountAuthorized = new ArrayList<Baggage>();
        for (int i = 0; i < BAGGAGE_AMOUNT_AUTHORIZED; i++) {
            baggagesCountAuthorized.add(baggageMock);
        }

        passengerWithMaximalBaggageAmountAuthorized =
                new Passenger(VALID_PASSENGER_HASH, VALID_PASSENGER_SEAT_CLASS, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, baggagesCountAuthorized);
    }

    @Test
    public void givenNewPassenger_whenGettingPassengerID_shouldReturnPassengerID(){
        UUID actualID = passengerWithoutBaggage.getHash();
        UUID expectedID = VALID_PASSENGER_HASH;
        assertEquals(expectedID, actualID);
    }

    @Test
    public void givenNewPassenger_whenComparingSeatClass_shouldBeTheSameAsTheOneWeInputed(){
        boolean actualSeatClass = passengerWithoutBaggage.isSameSeatClass(VALID_PASSENGER_SEAT_CLASS);
        assertTrue(actualSeatClass);
    }

    @Test
    public void givenPassengerWithBaggages_whenGettingBaggagesCount_shouldBeTheSameAsHisNumberOfBaggages() {
        int expectedCount = BAGGAGE_AMOUNT_AUTHORIZED;
        int actualCount = passengerWithMaximalBaggageAmountAuthorized.getBaggageCount();
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void givenNewPassengerWithNoBaggage_whenAddingBaggage_shouldHaveOneBaggage() {
        passengerWithoutBaggage.addBaggage(baggageMock);

        int expectedCount = 1;
        int actualCount = passengerWithoutBaggage.getBaggageCount();
        assertEquals(expectedCount, actualCount);
    }

    @Test(expected = BaggageAmountAuthorizedException.class)
    public void givenPassengerWithBaggageAmountAuthorized_whenAddingBaggage_shouldNotAddBaggage() {
        passengerWithMaximalBaggageAmountAuthorized.addBaggage(baggageMock);
    }

    @Test
    public void givenAPassengerWithoutBaggage_whenGettingBasePrice_shouldReturnFirstBaggageBasePrice() {
        float actualBasePrice = passengerWithoutBaggage.getBaggageBasePrice();
        assertTrue(FIRST_BAGGAGE_BASE_PRICE == actualBasePrice);
    }

    @Test
    public void givenAPassengerWithMoreThanOneBaggage_whenGettingBasePrice_shouldReturnAdditionalBasePrice() {
        float actualBasePrice = passengerWithMaximalBaggageAmountAuthorized.getBaggageBasePrice();
        assertTrue(ADDITIONAL_BAGGAGE_BASE_PRICE == actualBasePrice);
    }

    //TODO: le test ne verifie pas tellement le retour dans la liste, misere a mocker un baggage et rapatrier ses attributs settes par la fonction
    @Test
    public void givenNewPassengerWithNoBaggage_whenAddingBaggage_shouldHaveOneBaggageWithFirstBaggageBasePrice() {
        passengerWithoutBaggage.addBaggage(baggageMock);

        verify(baggageMock).setPrice(FIRST_BAGGAGE_BASE_PRICE);
    }

    //TODO: le test ne verifie pas tellement le retour dans la liste, misere a mocker un baggage et rapatrier ses attributs settes par la fonction
    @Test
    public void givenNewPassengerWithNoBaggage_whenAddingTwoBaggages_shouldHaveFirstBaggageWithFirstBasePriceAndNextWithAdditionalBasePrice() {
        passengerWithoutBaggage.addBaggage(baggageMock);
        passengerWithoutBaggage.addBaggage(baggageMock);

        verify(baggageMock, times(1)).setPrice(FIRST_BAGGAGE_BASE_PRICE);
        verify(baggageMock, times(1)).setPrice(ADDITIONAL_BAGGAGE_BASE_PRICE);
    }

    @Test
    public void givenNewPassenger_whenGettingPassengerFlightDate_shouldReturnFlightDate() {
        Instant actualFlightDate = passengerWithoutBaggage.getFlightDate();

        Instant expectedFlightDate = VALID_FLIGHT_DATE;
        assertEquals(expectedFlightDate, actualFlightDate);
    }

    @Test
    public void givenNewPassenger_whenGettingPassengerFlightNumber_shouldReturnPassengerFlightNumber() {
        String actualFlightNumber = passengerWithoutBaggage.getFlightNumber();

        String expectedFlightNumber = VALID_FLIGHT_NUMBER;
        assertEquals(expectedFlightNumber, actualFlightNumber);
    }
}