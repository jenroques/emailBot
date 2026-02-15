package com.enterprise.inbox.auth.api;

import java.util.List;

public record MeResponse(
        String username,
        List<String> roles
) {
}
