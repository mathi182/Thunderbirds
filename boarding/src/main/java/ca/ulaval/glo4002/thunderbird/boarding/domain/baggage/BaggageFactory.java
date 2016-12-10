package ca.ulaval.glo4002.thunderbird.boarding.domain.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.checked.CheckedBaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.speciality.Sport;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.NormalizedBaggageDTO;

public class BaggageFactory {
    private static final String CHECKED = "checked";
    private static final String STANDARD = "standard";
    private static final String PERSONAL = "personal";
    private static final String MEDICAL = "medical";
    private static final String SPORT = "sport";
    CheckedBaggageFactory checkedBaggageFactory = ServiceLocator.resolve(CheckedBaggageFactory.class);

    public Baggage createBaggage(Passenger passenger, NormalizedBaggageDTO dto) {
        Baggage createdBaggage = null;
        switch (dto.type) {
            case CHECKED:
                return checkedBaggageFactory.createCheckedBaggage(passenger, dto);
            case MEDICAL:
                return new Medical(dto.length, dto.mass, MEDICAL);
            case PERSONAL:
                return new Personal(dto.length, dto.mass, PERSONAL);
            case STANDARD:
                return new Standard(dto.length, dto.mass, STANDARD);
            case SPORT:
                Baggage sportBaggage = checkedBaggageFactory.createCheckedBaggage(passenger, dto);
                sportBaggage.addSpeciality(new Sport());
                return sportBaggage;
        }
        return createdBaggage;

    }


}
