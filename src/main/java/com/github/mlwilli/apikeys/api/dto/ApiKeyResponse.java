package com.github.mlwilli.apikeys.api.dto;

import com.github.mlwilli.apikeys.domain.KeyStatus;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents stored key metadata (no plaintext).
 */
public class ApiKeyResponse {

    private UUID id;
    private KeyStatus status;
    private Instant createdAt;
    private Instant revokedAt;

    public ApiKeyResponse() {
    }

    public UUID getId() {
        return id;
    }

    public ApiKeyResponse setId(UUID id) {
        this.id = id;
        return this;
    }

    public KeyStatus getStatus() {
        return status;
    }

    public ApiKeyResponse setStatus(KeyStatus status) {
        this.status = status;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ApiKeyResponse setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }

    public ApiKeyResponse setRevokedAt(Instant revokedAt) {
        this.revokedAt = revokedAt;
        return this;
    }
}
