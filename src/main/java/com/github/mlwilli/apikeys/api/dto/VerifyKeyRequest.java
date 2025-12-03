package com.github.mlwilli.apikeys.api.dto;

import jakarta.validation.constraints.NotBlank;

public class VerifyKeyRequest {

    @NotBlank
    private String apiKey;

    public VerifyKeyRequest() {
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
