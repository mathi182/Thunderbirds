package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageDTO;

public class BaggageFactory {
    public static final String CHECKED = "checked";
    CheckedBaggageFactory checkedBaggageFactory = ServiceLocator.resolve(CheckedBaggageFactory.class);

    public Baggage createBaggage(Passenger passenger, RegisterBaggageDTO dto) {
        Baggage createdBaggage = null;
        switch (dto.type) {
            case CHECKED:
                return checkedBaggageFactory.createCheckedBaggage(passenger,dto);
            case "medical":
                return new Medical();
            case "personal":
                return new Personal();
            case "standard":
                return new Standard();
        }
        return createdBaggage;

    }


}
