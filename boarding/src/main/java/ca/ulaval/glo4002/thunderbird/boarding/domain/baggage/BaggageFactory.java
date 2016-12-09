package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageDTO;

public class BaggageFactory {

    public Baggage createBaggage(Passenger passenger, RegisterBaggageDTO dto) {
        Baggage createdBaggage = null;
        switch (dto.type) {
            case "checked":
                //createdBaggage;
                break;
        }
        return createdBaggage;

    }


}
