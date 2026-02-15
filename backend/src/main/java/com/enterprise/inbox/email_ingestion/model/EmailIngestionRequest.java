package com.enterprise.inbox.email_ingestion.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailIngestionRequest(
        @NotBlank String messageId,
        @Email @NotBlank String sender,
        @NotBlank String subject,
        @NotBlank String body
) {
}
