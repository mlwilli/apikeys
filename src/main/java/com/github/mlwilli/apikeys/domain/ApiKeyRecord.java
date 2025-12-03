package com.github.mlwilli.apikeys.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a single API key for a client (stored hashed).
 */
public final class ApiKeyRecord {

    private final UUID id;
    private final String hashedKey;
    private final KeyStatus status;
    private final Instant createdAt;
    private final Instant revokedAt;

    public ApiKeyRecord(UUID id,
                        String hashedKey,
                        KeyStatus status,
                        Instant createdAt,
                        Instant revokedAt) {
        this.id = id != null ? id : UUID.randomUUID();
        this.hashedKey = Objects.requireNonNull(hashedKey, "hashedKey");
        this.status = Objects.requireNonNull(status, "status");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
        this.revokedAt = revokedAt;
    }

    public UUID id() {
        return id;
    }

    public String hashedKey() {
        return hashedKey;
    }

    public KeyStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant revokedAt() {
        return revokedAt;
    }

    public ApiKeyRecord withStatus(KeyStatus status, Instant revokedAt) {
        return new ApiKeyRecord(id, hashedKey, status, createdAt, revokedAt);
    }
}
