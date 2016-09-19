package ca.ulaval.glo4002.thunderbird.data.repository;

import ca.ulaval.glo4002.thunderbird.data.entity.Identity;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T extends Identity> {
    Optional<T> get(String id);

    Collection<T> get();

    default void save(Collection<T> entities) {
        entities.forEach(this::save);
    }

    void save(T entity);

    default void remove(Collection<T> entities) {
        entities.forEach(this::remove);
    }

    default void remove(T entity) {
        remove(entity.id);
    }

    void remove(String id);
}