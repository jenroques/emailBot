package com.enterprise.inbox.audit.api;

import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audit/admin")
public class AuditAdminController {

    @GetMapping("/events")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> events() {
        return Map.of("status", "ok", "message", "Admin audit event view");
    }
}
