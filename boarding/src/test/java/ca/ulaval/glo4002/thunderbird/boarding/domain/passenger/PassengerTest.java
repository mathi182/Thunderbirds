package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.BaggageAmountAuthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;
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
    private static final boolean NOT_VIP = false;
    private static final int BAGGAGE_LINEAR_DIMENSION_IN_MM = 10;
    private static final int BAGGAGE_WEIGHT_IN_G = 10;
    private static final String BAGGAGE_TYPE = "checked";

    private Passenger passengerWithoutBaggage;
    private Passenger passengerWithMaximalBaggageAmountAuthorized;

    @Before
    public void setup(){
        passengerWithoutBaggage = new Passenger(VALID_PASSENGER_HASH, VALID_PASSENGER_SEAT_CLASS,
                VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, NOT_VIP);

        passengerWithMaximalBaggageAmountAuthorized = new Passenger(VALID_PASSENGER_HASH,
                VALID_PASSENGER_SEAT_CLASS, VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, NOT_VIP);

        for (int i = 0; i < BAGGAGE_AMOUNT_AUTHORIZED; i++) {
            passengerWithMaximalBaggageAmountAuthorized.addBaggage(mock(Baggage.class));
        }
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
        int actualCount = passengerWithMaximalBaggageAmountAuthorized.getBaggageCount();

        assertEquals(BAGGAGE_AMOUNT_AUTHORIZED, actualCount);
    }

    @Test
    public void givenNewPassengerWithNoBaggage_whenAddingBaggage_shouldHaveOneBaggage() {
        passengerWithoutBaggage.addBaggage(mock(Baggage.class));

        int expectedCount = 1;
        int actualCount = passengerWithoutBaggage.getBaggageCount();
        assertEquals(expectedCount, actualCount);
    }

    @Test(expected = BaggageAmountAuthorizedException.class)
    public void givenPassengerWithBaggageAmountAuthorized_whenAddingBaggage_shouldNotAddBaggage() {
        passengerWithMaximalBaggageAmountAuthorized.addBaggage(mock(Baggage.class));
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

    @Test
    public void givenNewPassengerWithNoBaggage_whenAddingBaggage_shouldHaveOneBaggageWithFirstBaggageBasePrice() {
        Baggage baggage = new Baggage(BAGGAGE_LINEAR_DIMENSION_IN_MM, BAGGAGE_WEIGHT_IN_G, BAGGAGE_TYPE);
        passengerWithoutBaggage.addBaggage(baggage);

        Baggage firstBaggage = passengerWithoutBaggage.getBaggages().get(0);
        float actualPrice = firstBaggage.getPrice();
        assertTrue(FIRST_BAGGAGE_BASE_PRICE == actualPrice);
    }
    
    @Test
    public void givenNewPassengerWithNoBaggage_whenAddingTwoBaggages_shouldHaveSecondBaggageWithAdditionalBasePrice() {
        Baggage firstBaggage = new Baggage(BAGGAGE_LINEAR_DIMENSION_IN_MM, BAGGAGE_WEIGHT_IN_G, BAGGAGE_TYPE);
        passengerWithoutBaggage.addBaggage(firstBaggage);
        Baggage secondBaggage = new Baggage(BAGGAGE_LINEAR_DIMENSION_IN_MM, BAGGAGE_WEIGHT_IN_G, BAGGAGE_TYPE);
        passengerWithoutBaggage.addBaggage(secondBaggage);

        Baggage secondBaggageFromPassenger = passengerWithoutBaggage.getBaggages().get(1);
        float secondBaggageActualPrice = secondBaggageFromPassenger.getPrice();
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