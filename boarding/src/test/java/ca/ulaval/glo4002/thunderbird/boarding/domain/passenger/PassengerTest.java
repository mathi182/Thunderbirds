package ca.ulaval.glo4002.thunderbird.boarding.domain.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageType;
import ca.ulaval.glo4002.thunderbird.boarding.domain.seat.Seat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageType.CARRY_ON;
import static ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageType.CHECKED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class PassengerTest {

    private static final UUID VALID_PASSENGER_HASH = UUID.randomUUID();
    private static final Seat.SeatClass VALID_PASSENGER_SEAT_CLASS = Seat.SeatClass.ECONOMY;
    private static final Instant VALID_FLIGHT_DATE = Instant.ofEpochMilli(new Date().getTime());
    private static final String VALID_FLIGHT_NUMBER = "QK-918";
    public static final int VALID_BAGGAGE_COUNT = 3;

    private Passenger passengerWithoutBaggage;
    private Passenger passengerWithMaximalBaggageAmountAuthorized;
    private Passenger passengerWithTwoCheckedBaggageAndOneCarryOnBaggae;
    private Baggage baggageMock;

    @Before
    public void setup(){
        passengerWithoutBaggage = new Passenger(VALID_PASSENGER_HASH, VALID_PASSENGER_SEAT_CLASS,
                                                VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER);

        baggageMock = mock(Baggage.class);

        List<Baggage> baggages = new ArrayList<>();
        for (int i = 0; i < VALID_BAGGAGE_COUNT; i++) {
            baggages.add(baggageMock);
        }

        passengerWithMaximalBaggageAmountAuthorized = new Passenger(VALID_PASSENGER_HASH, VALID_PASSENGER_SEAT_CLASS,
                                                                    VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, baggages);

        Baggage checkedBaggageMock = mock(Baggage.class);
        willReturn(BaggageType.CHECKED).given(checkedBaggageMock).getType();

        Baggage carryOnBaggageMock = mock(Baggage.class);
        willReturn(BaggageType.CARRY_ON).given(carryOnBaggageMock).getType();

        baggages = new ArrayList<>();
        baggages.add(carryOnBaggageMock);
        baggages.add(checkedBaggageMock);
        baggages.add(checkedBaggageMock);

        passengerWithTwoCheckedBaggageAndOneCarryOnBaggae = new Passenger(VALID_PASSENGER_HASH, VALID_PASSENGER_SEAT_CLASS,
                                                                    VALID_FLIGHT_DATE, VALID_FLIGHT_NUMBER, baggages);
    }

    @Test
    public void givenPassengerWithTwoCheckedBaggageAndOneCarryOn_whenCountingBaggageOfType_shouldCountProperly() {
        int checkedBaggageCount = passengerWithTwoCheckedBaggageAndOneCarryOnBaggae.countBaggageOfType(CHECKED);
        int carryOnCount = passengerWithTwoCheckedBaggageAndOneCarryOnBaggae.countBaggageOfType(CARRY_ON);

        assertEquals(2, checkedBaggageCount);
        assertEquals(1, carryOnCount);
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
    public void givenPassengerWithValidBaggageCount_whenGettingBaggagesCount_shouldBeValidCount() {
        int actualCount = passengerWithMaximalBaggageAmountAuthorized.getBaggageCount();

        assertEquals(VALID_BAGGAGE_COUNT, actualCount);
    }

    @Test
    @Ignore
    public void givenNewPassengerWithNoBaggage_whenAddingBaggage_shouldHaveOneBaggage() {
        // TODO:
        passengerWithoutBaggage.addBaggage(baggageMock);

        int expectedCount = 1;
        int actualCount = passengerWithoutBaggage.getBaggageCount();
        assertEquals(expectedCount, actualCount);
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