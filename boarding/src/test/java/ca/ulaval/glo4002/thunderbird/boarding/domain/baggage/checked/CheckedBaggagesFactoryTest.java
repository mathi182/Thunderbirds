package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;

public class CheckedBaggagesFactoryTest {
    @Test
    public void givenEconomicSeatClass_whenGetCheckedBaggages_shouldReturnEconomicCheckedBaggages() {
        Seat.SeatClass seatClass = Seat.SeatClass.ECONOMY;

        CheckedBaggages checkedBaggages = CheckedBaggagesFactory.getCheckedBaggages(seatClass);

        Assert.assertThat(checkedBaggages, instanceOf(CheckedBaggages.class));
    }

    @Test
    public void givenBusinessSeatClass_whenGetCheckedBaggages_shouldReturnBusinessCheckedBaggages() {
        Seat.SeatClass seatClass = Seat.SeatClass.BUSINESS;

        CheckedBaggages checkedBaggages = CheckedBaggagesFactory.getCheckedBaggages(seatClass);

        Assert.assertThat(checkedBaggages, instanceOf(CheckedBaggages.class));
    }
}