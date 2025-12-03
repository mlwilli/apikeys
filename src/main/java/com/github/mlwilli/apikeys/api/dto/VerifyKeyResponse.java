package com.github.mlwilli.apikeys.api.dto;

public class VerifyKeyResponse {

    private boolean valid;

    public VerifyKeyResponse() {
    }

    public VerifyKeyResponse(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public VerifyKeyResponse setValid(boolean valid) {
        this.valid = valid;
        return this;
    }
}
