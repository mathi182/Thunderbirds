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

public class BaggageApplication {

    private BaggagesListAssembler baggagesListAssembler;

    public BaggageApplication() {
        this.baggagesListAssembler = new BaggagesListAssembler();

    }

    private void validateBaggage(Baggage baggage, Passenger passenger) {
        BaggageValidationStrategyFactory factory = new BaggageValidationStrategyFactory();
        BaggageValidationStrategy strategy = factory.getStrategy(passenger.getSeatClass());
        strategy.validateBaggage(baggage);
    }

    public void registerBaggage(UUID passengerHash, RegisterBaggageRequest request) {
        Passenger passenger = getPassenger(passengerHash);
        Baggage baggage = convertRequestToBaggage(request);

        validateBaggage(baggage, passenger);
        passenger.addBaggage(baggage);
    }

    private Baggage convertRequestToBaggage(RegisterBaggageRequest request) {
        RegisterBaggageRequestAssembler registerBaggageRequestAssembler = new RegisterBaggageRequestAssembler();
        return registerBaggageRequestAssembler.getDomainBaggage(request);
    }

    private Passenger getPassenger(UUID passengerHash) {
        PassengerRepository repository = ServiceLocator.resolve(PassengerRepository.class);
        return repository.getPassenger(passengerHash);
    }

    public BaggagesListDTO getBaggagesListDTOFromPassenger(String passengerHash) {
        Passenger passenger = getPassenger(UUID.fromString(passengerHash));
        List<Baggage> baggages = passenger.getBaggages();
        return baggagesListAssembler.toDTO(baggages);
    }
}
