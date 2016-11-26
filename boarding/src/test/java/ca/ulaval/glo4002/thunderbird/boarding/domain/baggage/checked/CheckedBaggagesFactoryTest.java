package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.instanceOf;

public class CheckedBaggagesFactoryTest {
    private final static UUID SOME_PASSENGER_HASH = new UUID(1L, 2L);

    @Test
    public void givenEconomicSeatClass_whenGetCheckedBaggages_shouldReturnEconomicCheckedBaggages() {
        Seat.SeatClass seatClass = Seat.SeatClass.ECONOMY;

        CheckedBaggages checkedBaggages = CheckedBaggagesFactory.getCheckedBaggages(SOME_PASSENGER_HASH, seatClass);

        Assert.assertThat(checkedBaggages, instanceOf(EconomicCheckedBaggages.class));
    }

    @Test
    public void givenBusinessSeatClass_whenGetCheckedBaggages_shouldReturnBusinessCheckedBaggages() {
        Seat.SeatClass seatClass = Seat.SeatClass.BUSINESS;

        CheckedBaggages checkedBaggages = CheckedBaggagesFactory.getCheckedBaggages(SOME_PASSENGER_HASH, seatClass);

        Assert.assertThat(checkedBaggages, instanceOf(BusinessCheckedBaggages.class));
    }
}