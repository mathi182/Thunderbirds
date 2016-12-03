package ca.ulaval.glo4002.thunderbird.boarding.application.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.exceptions.PassengerNotCheckedInException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;

import java.util.UUID;

public class BaggageApplicationService {
    private PassengerRepository repository;

    public BaggageApplicationService(PassengerRepository repository) {
        this.repository = repository;
    }

    public BaggageApplicationService() {
        this.repository = ServiceLocator.resolve(PassengerRepository.class);
    }

    public UUID registerBaggage(UUID passengerHash, Baggage baggage) {
        Passenger passenger = getPassenger(passengerHash);
        passenger.addBaggage(baggage);
        repository.savePassenger(passenger);

        return baggage.getId();
    }

    public Passenger getPassenger(UUID passengerHash) {
        Passenger passenger = repository.findByPassengerHash(passengerHash);
        if (!passenger.isCheckedIn()) {
            throw new PassengerNotCheckedInException();
        }
        return passenger;
    }
}