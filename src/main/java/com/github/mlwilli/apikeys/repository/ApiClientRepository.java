package com.github.mlwilli.apikeys.repository;

import com.github.mlwilli.apikeys.domain.ApiClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApiClientRepository {

    ApiClient save(ApiClient client);

    Optional<ApiClient> findById(UUID id);

    Optional<ApiClient> findByName(String name);

    List<ApiClient> findAll();
}
