package com.github.mlwilli.apikeys.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Generates API keys using secure random bytes and URL-safe Base64 encoding.
 */
public final class KeyGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int KEY_BYTES = 32; // 256 bits

    private KeyGenerator() {
    }

    public static String generateApiKey() {
        byte[] bytes = new byte[KEY_BYTES];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
