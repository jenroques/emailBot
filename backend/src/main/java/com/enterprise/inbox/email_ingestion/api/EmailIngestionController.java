package com.enterprise.inbox.email_ingestion.api;

import com.enterprise.inbox.email_ingestion.model.EmailIngestionRequest;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingestion")
public class EmailIngestionController {

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    public Map<String, String> ingest(@Valid @RequestBody EmailIngestionRequest request) {
        return Map.of("status", "accepted", "messageId", request.messageId());
    }
}
