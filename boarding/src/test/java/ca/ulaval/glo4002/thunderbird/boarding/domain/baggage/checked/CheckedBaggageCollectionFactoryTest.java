package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.BusinessBaggageCollection;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.CheckedBaggageCollection;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.CheckedBaggageCollectionFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.collection.EconomicBaggageCollection;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class CheckedBaggageCollectionFactoryTest {
    private final Passenger passenger = mock(Passenger.class);

    @Test
    public void givenEconomicSeatClass_whenGettingCheckedBaggages_shouldReturnEconomicCheckedBaggages() {
        willReturn(Seat.SeatClass.ECONOMY).given(passenger).getSeatClass();

        CheckedBaggageCollection checkedBaggageCollection = CheckedBaggageCollectionFactory.createCheckedBaggages(passenger);

        Assert.assertThat(checkedBaggageCollection, instanceOf(EconomicBaggageCollection.class));
    }

    @Test
    public void givenBusinessSeatClass_whenGettingCheckedBaggages_shouldReturnBusinessCheckedBaggages() {
        willReturn(Seat.SeatClass.BUSINESS).given(passenger).getSeatClass();

        CheckedBaggageCollection checkedBaggageCollection = CheckedBaggageCollectionFactory.createCheckedBaggages(passenger);

        Assert.assertThat(checkedBaggageCollection, instanceOf(BusinessBaggageCollection.class));
    }
}