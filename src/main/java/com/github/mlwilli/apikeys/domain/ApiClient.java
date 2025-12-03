package com.github.mlwilli.apikeys.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * API client that owns one or more API keys.
 */
public final class ApiClient {

    private final UUID id;
    private final String name;
    private final boolean active;
    private final Instant createdAt;
    private final List<ApiKeyRecord> keys;

    public ApiClient(UUID id,
                     String name,
                     boolean active,
                     Instant createdAt,
                     List<ApiKeyRecord> keys) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = Objects.requireNonNull(name, "name");
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
        this.keys = new ArrayList<>(Objects.requireNonNull(keys, "keys"));
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public boolean active() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public List<ApiKeyRecord> keys() {
        return List.copyOf(keys);
    }

    public ApiClient addKey(ApiKeyRecord key) {
        List<ApiKeyRecord> newKeys = new ArrayList<>(keys);
        newKeys.add(key);
        return new ApiClient(id, name, active, createdAt, newKeys);
    }

    public ApiClient replaceKeys(List<ApiKeyRecord> newKeys) {
        return new ApiClient(id, name, active, createdAt, newKeys);
    }

    public ApiClient deactivate() {
        return new ApiClient(id, name, false, createdAt, keys);
    }
}
