package ca.ulaval.glo4002.thunderbird.boarding.application.baggage;

import ca.ulaval.glo4002.thunderbird.boarding.application.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.application.baggage.exceptions.PassengerNotCheckedInException;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.Baggage;
import ca.ulaval.glo4002.thunderbird.boarding.domain.baggage.BaggageFactory;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.PassengerRepository;
import ca.ulaval.glo4002.thunderbird.boarding.rest.baggage.RegisterBaggageDTO;

import java.util.UUID;

public class BaggageApplicationService {
    private PassengerRepository repository;
    private BaggageFactory baggageFactory;

    public BaggageApplicationService(PassengerRepository repository, BaggageFactory baggageFactory) {
        this.repository = repository;
        this.baggageFactory = baggageFactory;
    }

    public BaggageApplicationService() {
        this.repository = ServiceLocator.resolve(PassengerRepository.class);
        this.baggageFactory = ServiceLocator.resolve(BaggageFactory.class);
    }

    public UUID registerBaggage(UUID passengerHash, RegisterBaggageDTO registerBaggageDTO) {
        Passenger passenger = getPassenger(passengerHash);
        Baggage baggage = baggageFactory.createBaggage(passenger, registerBaggageDTO);
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