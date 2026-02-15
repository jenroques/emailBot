package com.enterprise.inbox.auth.api;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}
