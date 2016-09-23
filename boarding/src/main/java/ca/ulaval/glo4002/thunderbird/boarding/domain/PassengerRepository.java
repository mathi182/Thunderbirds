package ca.ulaval.glo4002.thunderbird.boarding.domain;

import ca.ulaval.glo4002.thunderbird.boarding.Passenger;
import ca.ulaval.glo4002.thunderbird.boarding.database.PassengerRepositoryImpl;

public interface PassengerRepository extends Repository<Passenger> {
    PassengerRepository repository = new PassengerRepositoryImpl();
    
    static PassengerRepository getInstance() {
        return repository;
    }
}
