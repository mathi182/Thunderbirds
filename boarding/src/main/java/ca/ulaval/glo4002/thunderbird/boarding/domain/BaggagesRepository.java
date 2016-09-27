package ca.ulaval.glo4002.thunderbird.boarding.domain;

import ca.ulaval.glo4002.thunderbird.boarding.database.BaggagesRepositoryImpl;

public interface BaggagesRepository extends Repository<Identity> {
    BaggagesRepository repository = new BaggagesRepositoryImpl();

    static BaggagesRepository getInstance() {
        return repository;
    }
}
