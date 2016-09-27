package ca.ulaval.glo4002.thunderbird.boarding.domain;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T extends Identity> {
    Optional<T> findById(String id);

    Collection<T> findAll();

    void save(T entity);

    default void remove(T entity) {
        remove(entity.getId());
    }

    void remove(String id);
}