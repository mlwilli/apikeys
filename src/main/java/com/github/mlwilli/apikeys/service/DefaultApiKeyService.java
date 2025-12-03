package com.github.mlwilli.apikeys.service;

import com.github.mlwilli.apikeys.domain.ApiClient;
import com.github.mlwilli.apikeys.domain.ApiKeyRecord;
import com.github.mlwilli.apikeys.domain.KeyStatus;
import com.github.mlwilli.apikeys.repository.ApiClientRepository;
import com.github.mlwilli.apikeys.util.HashUtils;
import com.github.mlwilli.apikeys.util.KeyGenerator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultApiKeyService implements ApiKeyService {

    private final ApiClientRepository repository;

    public DefaultApiKeyService(ApiClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public ApiClient createClient(String name) {
        repository.findByName(name).ifPresent(existing -> {
            throw new IllegalArgumentException("Client with name already exists: " + name);
        });

        ApiClient client = new ApiClient(
                null,
                name,
                true,
                Instant.now(),
                List.of()
        );
        return repository.save(client);
    }

    @Override
    public List<ApiClient> listClients() {
        return repository.findAll();
    }

    @Override
    public ApiClient getClient(UUID clientId) {
        return repository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + clientId));
    }

    @Override
    public String generateApiKey(UUID clientId) {
        ApiClient client = getClient(clientId);

        String apiKey = KeyGenerator.generateApiKey();
        String hash = HashUtils.sha256(apiKey);

        ApiKeyRecord record = new ApiKeyRecord(
                null,
                hash,
                KeyStatus.ACTIVE,
                Instant.now(),
                null
        );

        ApiClient updated = client.addKey(record);
        repository.save(updated);
        return apiKey; // plaintext returned only once
    }

    @Override
    public List<ApiKeyRecord> listKeys(UUID clientId) {
        ApiClient client = getClient(clientId);
        return client.keys();
    }

    @Override
    public void revokeKey(UUID clientId, UUID keyId) {
        ApiClient client = getClient(clientId);
        List<ApiKeyRecord> updatedKeys = new ArrayList<>();

        boolean found = false;
        for (ApiKeyRecord key : client.keys()) {
            if (key.id().equals(keyId)) {
                found = true;
                ApiKeyRecord revoked = key.withStatus(KeyStatus.REVOKED, Instant.now());
                updatedKeys.add(revoked);
            } else {
                updatedKeys.add(key);
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Key not found for client: " + keyId);
        }

        ApiClient updated = client.replaceKeys(updatedKeys);
        repository.save(updated);
    }

    @Override
    public boolean verifyApiKey(String presentedKey) {
        if (presentedKey == null || presentedKey.isBlank()) {
            return false;
        }
        String hash = HashUtils.sha256(presentedKey);

        return repository.findAll().stream()
                .flatMap(client -> client.keys().stream())
                .anyMatch(key -> key.status() == KeyStatus.ACTIVE &&
                        key.hashedKey().equals(hash));
    }
}
