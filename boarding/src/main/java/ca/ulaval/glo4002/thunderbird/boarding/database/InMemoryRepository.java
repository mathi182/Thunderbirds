package ca.ulaval.glo4002.thunderbird.boarding.database;

import ca.ulaval.glo4002.thunderbird.boarding.domain.Identity;
import ca.ulaval.glo4002.thunderbird.boarding.domain.Repository;

import java.util.*;

public abstract class InMemoryRepository<T extends Identity> implements Repository<T> {
    private Map<String, T> entities = new HashMap<>();

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(entities.getOrDefault(id, null));
    }

    @Override
    public Collection<T> findAll() {
        return Collections.unmodifiableCollection(entities.values());
    }

    @Override
    public void save(T entity) {
        entities.put(entity.getId(), entity);
    }

    @Override
    public void remove(String id) {
        entities.remove(id);
    }
}