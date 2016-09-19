package ca.ulaval.glo4002.thunderbird.data.repository;

import ca.ulaval.glo4002.thunderbird.data.entity.Identity;

import java.util.*;

public abstract class InMemoryRepository<T extends Identity> implements Repository<T> {
    private Map<String, T> entities = new HashMap<>();

    @Override
    public Optional<T> get(String id) {
        return Optional.ofNullable(entities.getOrDefault(id, null));
    }

    @Override
    public Collection<T> get() {
        return Collections.unmodifiableCollection(entities.values());
    }

    @Override
    public void save(T entity) {
        entities.put(entity.id, entity);
    }

    @Override
    public void remove(String id) {
        entities.remove(id);
    }
}