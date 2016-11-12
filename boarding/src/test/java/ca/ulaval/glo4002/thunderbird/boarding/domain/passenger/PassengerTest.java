package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.luggage.Luggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.LuggageAmountAuthorizedException;
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
import static org.mockito.Mockito.mock;

public class PassengerTest {
    private static final UUID VALID_PASSENGER_HASH = UUID.randomUUID();
    private static final Seat.SeatClass VALID_PASSENGER_SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final Instant VALID_FLIGHT_DATE = Instant.ofEpochMilli(new Date().getTime());
    private static final String VALID_FLIGHT_NUMBER = "QK-918";
    private static final int LUGGAGE_LINEAR_DIMENSION_IN_MM = 10;
    private static final int LUGGAGE_WEIGHT_IN_G = 10;
    private static final String BAGGAGE_TYPE = "checked";
    private static final int FIRST_BAGGAGE = 0;
    private static final int SECOND_BAGGAGE = 1;

    private Passenger passengerWithoutBaggage;
    private Passenger passengerWithMaximalBaggageAmountAuthorized;
    private Luggage luggageMock;

    @Before
    public void setup(){
        passengerWithoutBaggage = new Passenger(VALID_PASSENGER_HASH, VALID_PASSENGER_SEAT_CLASS, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER);
        luggageMock = mock(Luggage.class);

        List<Luggage> baggagesCountAuthorized = new ArrayList<>();
        for (int i = 0; i < BAGGAGE_AMOUNT_AUTHORIZED; i++) {
            baggagesCountAuthorized.add(luggageMock);
        }

        passengerWithMaximalBaggageAmountAuthorized =
                new Passenger(VALID_PASSENGER_HASH, VALID_PASSENGER_SEAT_CLASS, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, baggagesCountAuthorized);
    }

    @Test
    public void givenNewPassenger_whenGettingPassengerID_shouldReturnPassengerID(){
        UUID actualID = passengerWithoutBaggage.getHash();

        assertEquals(VALID_PASSENGER_HASH, actualID);
    }

    @Test
    public void givenNewPassenger_whenComparingSeatClass_shouldBeTheSameAsTheOneWeInputed(){
        boolean actualSeatClass = passengerWithoutBaggage.isSameSeatClass(VALID_PASSENGER_SEAT_CLASS);

        assertTrue(actualSeatClass);
    }

    @Test
    public void givenPassengerWithBaggages_whenGettingBaggagesCount_shouldBeTheSameAsHisNumberOfBaggages() {
        int actualCount = passengerWithMaximalBaggageAmountAuthorized.getLuggagesCount();

        assertEquals(BAGGAGE_AMOUNT_AUTHORIZED, actualCount);
    }

    @Test
    public void givenNewPassengerWithNoBaggage_whenAddingBaggage_shouldHaveOneBaggage() {
        passengerWithoutBaggage.addBaggage(luggageMock);

        int expectedCount = 1;
        int actualCount = passengerWithoutBaggage.getLuggagesCount();
        assertEquals(expectedCount, actualCount);
    }

    @Test(expected = LuggageAmountAuthorizedException.class)
    public void givenPassengerWithBaggageAmountAuthorized_whenAddingBaggage_shouldNotAddBaggage() {
        passengerWithMaximalBaggageAmountAuthorized.addBaggage(luggageMock);
    }

    @Test
    public void givenAPassengerWithoutBaggage_whenGettingBasePrice_shouldReturnFirstBaggageBasePrice() {
        float actualBasePrice = passengerWithoutBaggage.getLuggageBasePrice();
        assertTrue(FIRST_BAGGAGE_BASE_PRICE == actualBasePrice);
    }

    @Test
    public void givenAPassengerWithMoreThanOneBaggage_whenGettingBasePrice_shouldReturnAdditionalBasePrice() {
        float actualBasePrice = passengerWithMaximalBaggageAmountAuthorized.getLuggageBasePrice();
        assertTrue(ADDITIONAL_BAGGAGE_BASE_PRICE == actualBasePrice);
    }

    @Test
    public void givenNewPassengerWithNoBaggage_whenAddingBaggage_shouldHaveOneBaggageWithFirstBaggageBasePrice() {
        Luggage luggage = new Luggage(LUGGAGE_LINEAR_DIMENSION_IN_MM, LUGGAGE_WEIGHT_IN_G, BAGGAGE_TYPE);
        passengerWithoutBaggage.addBaggage(luggage);

        List<Luggage> luggages = passengerWithoutBaggage.getLuggages();
        Luggage firstLuggage = luggages.get(FIRST_BAGGAGE);
        float actualPrice = firstLuggage.getPrice();

        assertTrue(FIRST_BAGGAGE_BASE_PRICE == actualPrice);
    }
    
    @Test
    public void givenNewPassengerWithNoBaggage_whenAddingTwoBaggages_shouldHaveSecondBaggageWithAdditionalBasePrice() {
        Luggage firstLuggage = new Luggage(LUGGAGE_LINEAR_DIMENSION_IN_MM, LUGGAGE_WEIGHT_IN_G, BAGGAGE_TYPE);
        Luggage secondLuggage = new Luggage(LUGGAGE_LINEAR_DIMENSION_IN_MM, LUGGAGE_WEIGHT_IN_G, BAGGAGE_TYPE);

        passengerWithoutBaggage.addBaggage(firstLuggage);
        passengerWithoutBaggage.addBaggage(secondLuggage);

        List<Luggage> luggages = passengerWithoutBaggage.getLuggages();
        Luggage secondLuggageFromPassenger = luggages.get(SECOND_BAGGAGE);
        float secondBaggageActualPrice = secondLuggageFromPassenger.getPrice();
        assertTrue(ADDITIONAL_BAGGAGE_BASE_PRICE == secondBaggageActualPrice);
    }

    @Test
    public void givenNewPassenger_whenGettingPassengerFlightDate_shouldReturnFlightDate() {
        Instant actualFlightDate = passengerWithoutBaggage.getFlightDate();

        assertEquals(VALID_FLIGHT_DATE, actualFlightDate);
    }

    @Test
    public void givenNewPassenger_whenGettingPassengerFlightNumber_shouldReturnPassengerFlightNumber() {
        String actualFlightNumber = passengerWithoutBaggage.getFlightNumber();

        assertEquals(VALID_FLIGHT_NUMBER, actualFlightNumber);
    }
}