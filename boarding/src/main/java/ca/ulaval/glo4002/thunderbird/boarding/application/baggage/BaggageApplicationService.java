package ca.ulaval.glo4002.thunderbird.boarding.application.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.BaggageValidationStrategy;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.validationStrategy.BaggageValidationStrategyFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.BaggagesListAssembler;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.BaggagesListDTO;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageRequest;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageRequestAssembler;

import java.util.List;
import java.util.UUID;

public class BaggageApplicationService {

    private BaggagesListAssembler baggagesListAssembler;
    private RegisterBaggageRequestAssembler registerBaggageRequestAssembler;
    private  BaggageValidationStrategyFactory factory;

    public BaggageApplicationService() {
        this.baggagesListAssembler = new BaggagesListAssembler();
        this.registerBaggageRequestAssembler = new RegisterBaggageRequestAssembler();
        this.factory = new BaggageValidationStrategyFactory();
    }

    public void registerBaggage(UUID passengerHash, RegisterBaggageRequest request) {
        Passenger passenger = getPassenger(passengerHash);
        Baggage baggage = registerBaggageRequestAssembler.getDomainBaggage(request);

        validateBaggage(baggage, passenger);
        passenger.addBaggage(baggage);
    }

    public BaggagesListDTO getBaggagesListDTOFromPassenger(String passengerHash) {
        Passenger passenger = getPassenger(UUID.fromString(passengerHash));
        List<Baggage> baggages = passenger.getBaggages();
        return baggagesListAssembler.toDTO(baggages);
    }

    private Passenger getPassenger(UUID passengerHash) {
        PassengerRepository repository = ServiceLocator.resolve(PassengerRepository.class);
        return repository.getPassenger(passengerHash);
    }

    private void validateBaggage(Baggage baggage, Passenger passenger) {
        BaggageValidationStrategy strategy = factory.getStrategy(passenger.getSeatClass());
        strategy.validateBaggage(baggage);
    }
}
