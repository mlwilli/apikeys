package com.github.mlwilli.apikeys.api;

import com.github.mlwilli.apikeys.api.dto.*;
import com.github.mlwilli.apikeys.domain.ApiClient;
import com.github.mlwilli.apikeys.domain.ApiKeyRecord;
import com.github.mlwilli.apikeys.service.ApiKeyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiKeyController {

    private final ApiKeyService service;

    public ApiKeyController(ApiKeyService service) {
        this.service = service;
    }

    // ---- Clients ----

    @PostMapping("/clients")
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody CreateClientRequest request) {
        ApiClient created = service.createClient(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(toClientResponse(created));
    }

    @GetMapping("/clients")
    public List<ClientResponse> listClients() {
        return service.listClients().stream()
                .map(this::toClientResponse)
                .toList();
    }

    @GetMapping("/clients/{clientId}")
    public ClientResponse getClient(@PathVariable UUID clientId) {
        return toClientResponse(service.getClient(clientId));
    }

    // ---- API Keys ----

    @PostMapping("/clients/{clientId}/keys")
    public ResponseEntity<String> generateKey(@PathVariable UUID clientId) {
        String plaintextKey = service.generateApiKey(clientId);
        // Return plaintext directly in body; in a real system you'd show it once and never again.
        return ResponseEntity.status(HttpStatus.CREATED).body(plaintextKey);
    }

    @GetMapping("/clients/{clientId}/keys")
    public List<ApiKeyResponse> listKeys(@PathVariable UUID clientId) {
        List<ApiKeyRecord> keys = service.listKeys(clientId);
        return keys.stream().map(this::toKeyResponse).toList();
    }

    @PostMapping("/clients/{clientId}/keys/{keyId}/revoke")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokeKey(@PathVariable UUID clientId, @PathVariable UUID keyId) {
        service.revokeKey(clientId, keyId);
    }

    // ---- Verification ----

    @PostMapping("/keys/verify")
    public VerifyKeyResponse verify(@Valid @RequestBody VerifyKeyRequest request) {
        boolean valid = service.verifyApiKey(request.getApiKey());
        return new VerifyKeyResponse(valid);
    }

    // ---- Error handling ----

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // ---- Mapping helpers ----

    private ClientResponse toClientResponse(ApiClient client) {
        return new ClientResponse()
                .setId(client.id())
                .setName(client.name())
                .setActive(client.active())
                .setCreatedAt(client.createdAt());
    }

    private ApiKeyResponse toKeyResponse(ApiKeyRecord key) {
        return new ApiKeyResponse()
                .setId(key.id())
                .setStatus(key.status())
                .setCreatedAt(key.createdAt())
                .setRevokedAt(key.revokedAt());
    }
}
