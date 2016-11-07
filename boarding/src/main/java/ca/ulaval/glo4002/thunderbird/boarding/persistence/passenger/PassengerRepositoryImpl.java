package ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger;

import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.domain.passenger.exceptions.PassengerNotFoundException;

import java.util.UUID;

public class PassengerRepositoryImpl implements PassengerRepository {
    @Override
    public Passenger getPassenger(UUID passengerHash) {
        throw new PassengerNotFoundException(passengerHash);
    }

    @Override
    public void savePassenger(Passenger passenger) {

    }
}
