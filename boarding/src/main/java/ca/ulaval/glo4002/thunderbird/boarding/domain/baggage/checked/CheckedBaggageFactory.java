package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked;

import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.strategies.OversizeBaggageStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.strategies.OverweightBaggageStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.exceptions.NoSuchStrategyException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;

public class CheckedBaggageFactory {

    private OversizeBaggageStrategy oversizeStrategy;
    private OverweightBaggageStrategy overweightStrategy;
    private static final String CHECKED = "checked";

    public CheckedBaggageFactory() {
        this.overweightStrategy = new OverweightBaggageStrategy();
        this.oversizeStrategy = new OversizeBaggageStrategy();
    }

    public CheckedBaggageFactory(OversizeBaggageStrategy oversizeStrategy, OverweightBaggageStrategy overweightStrategy) {
        this.overweightStrategy = overweightStrategy;
        this.oversizeStrategy = oversizeStrategy;
    }

    public Baggage createCheckedBaggage(Passenger passenger, NormalizedBaggageDTO dto) {

        switch (passenger.getSeatClass()) {
            case ECONOMY:
                Economic economicBaggage = new Economic(dto.length, dto.mass, CHECKED);
                overweightStrategy.applyStrategy(economicBaggage,Economic.MAX_WEIGHT);
                oversizeStrategy.applyStrategy(economicBaggage,Economic.MAX_LENGTH);
                return economicBaggage;
            case BUSINESS:
                Business businessBaggage = new Business(dto.length, dto.mass, CHECKED);
                overweightStrategy.applyStrategy(businessBaggage,Business.MAX_WEIGHT);
                oversizeStrategy.applyStrategy(businessBaggage,Business.MAX_LENGTH);
                return businessBaggage;
            default:
                throw new NoSuchStrategyException("unknown seatClass" + passenger.getSeatClass());
        }
    }
}
