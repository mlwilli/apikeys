package com.github.mlwilli.apikeys.api.dto;

import java.time.Instant;
import java.util.UUID;

public class ClientResponse {

    private UUID id;
    private String name;
    private boolean active;
    private Instant createdAt;

    public ClientResponse() {
    }

    public UUID getId() {
        return id;
    }

    public ClientResponse setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ClientResponse setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public ClientResponse setActive(boolean active) {
        this.active = active;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ClientResponse setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
