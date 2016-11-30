package ca.ulaval.glo4002.thunderbird.boarding.application.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.exceptions.PassengerNotCheckedInException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;

import java.util.UUID;

public class BaggageApplicationService {
    private final PassengerRepository repository;

    public BaggageApplicationService(PassengerRepository repository) {
        this.repository = repository;
    }

    public BaggageApplicationService() {
        this.repository = ServiceLocator.resolve(PassengerRepository.class);
    }

    public UUID registerBaggage(UUID passengerHash, Baggage baggage) {
        Passenger passenger = repository.getPassenger(passengerHash);
        if (!passenger.isCheckin()) {
            throw new PassengerNotCheckedInException();
        }

        passenger.addBaggage(baggage);

        return baggage.getId();
    }

    public Passenger getPassenger(UUID passengerHash) {
        return repository.getPassenger(passengerHash);
    }
}