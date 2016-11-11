package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.BaggageAmountAuthorizedException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Before;
import org.junit.Ignore;
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
    private static final int BAGGAGE_LINEAR_DIMENSION_IN_MM = 10;
    private static final int BAGGAGE_WEIGHT_IN_G = 10;
    private static final String BAGGAGE_TYPE = "checked";
    private static final int FIRST_BAGGAGE = 0;
    private static final int SECOND_BAGGAGE = 0;

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

    @Test
    public void givenNewPassengerWithNoBaggage_whenAddingBaggage_shouldHaveOneBaggageWithFirstBaggageBasePrice() {
        Baggage baggage = new Baggage(BAGGAGE_LINEAR_DIMENSION_IN_MM, BAGGAGE_WEIGHT_IN_G, BAGGAGE_TYPE);
        passengerWithoutBaggage.addBaggage(baggage);

        List<Baggage> baggages = passengerWithoutBaggage.getBaggages();
        Baggage firstBaggage = baggages.get(FIRST_BAGGAGE);
        float actualPrice = firstBaggage.getPrice();

        assertTrue(FIRST_BAGGAGE_BASE_PRICE == actualPrice);
    }
    
    @Ignore
    @Test
    public void givenNewPassengerWithNoBaggage_whenAddingTwoBaggages_shouldHaveFirstBaggageWithFirstBasePriceAndNextWithAdditionalBasePrice() {
        Baggage firstBaggage = new Baggage(BAGGAGE_LINEAR_DIMENSION_IN_MM, BAGGAGE_WEIGHT_IN_G, BAGGAGE_TYPE);
        Baggage secondBaggage = new Baggage(BAGGAGE_LINEAR_DIMENSION_IN_MM, BAGGAGE_WEIGHT_IN_G, BAGGAGE_TYPE);
        passengerWithoutBaggage.addBaggage(firstBaggage);
        passengerWithoutBaggage.addBaggage(secondBaggage);

        List<Baggage> baggages = passengerWithoutBaggage.getBaggages();
        //La valeur des prix quand on regarde dans la liste baggages est ok. Mais quand on get le baggage individuellement, elles deviennent invalide???
        Baggage firstBaggageFromPassenger = baggages.get(FIRST_BAGGAGE);
        Baggage secondBaggageFromPassenger = baggages.get(SECOND_BAGGAGE);
        float firstBaggageActualPrice = firstBaggageFromPassenger.getPrice();
        float secondBaggageActualPrice = secondBaggageFromPassenger.getPrice();

        assertTrue(FIRST_BAGGAGE_BASE_PRICE == firstBaggageActualPrice);
        assertTrue(ADDITIONAL_BAGGAGE_BASE_PRICE == secondBaggageActualPrice);
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