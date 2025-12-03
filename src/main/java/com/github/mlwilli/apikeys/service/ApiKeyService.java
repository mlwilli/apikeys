package com.github.mlwilli.apikeys.service;

import com.github.mlwilli.apikeys.domain.ApiClient;
import com.github.mlwilli.apikeys.domain.ApiKeyRecord;

import java.util.List;
import java.util.UUID;

public interface ApiKeyService {

    ApiClient createClient(String name);

    List<ApiClient> listClients();

    ApiClient getClient(UUID clientId);

    /**
     * Generates a new API key for the client, persists the hash, and returns the plaintext key once.
     */
    String generateApiKey(UUID clientId);

    List<ApiKeyRecord> listKeys(UUID clientId);

    void revokeKey(UUID clientId, UUID keyId);

    boolean verifyApiKey(String presentedKey);
}
