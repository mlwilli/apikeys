package com.github.mlwilli.apikeys.repository;

import com.github.mlwilli.apikeys.domain.ApiClient;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryApiClientRepository implements ApiClientRepository {

    private final Map<UUID, ApiClient> store = new ConcurrentHashMap<>();

    @Override
    public ApiClient save(ApiClient client) {
        store.put(client.id(), client);
        return client;
    }

    @Override
    public Optional<ApiClient> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<ApiClient> findByName(String name) {
        return store.values().stream()
                .filter(c -> c.name().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<ApiClient> findAll() {
        return new ArrayList<>(store.values());
    }
}
