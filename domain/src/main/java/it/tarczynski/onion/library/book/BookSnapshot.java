package it.tarczynski.onion.library.book;

import lombok.Builder;

import java.time.Instant;

@Builder
public record BookSnapshot(String id,
                           String authorId,
                           String title,
                           Instant createdAt,
                           Instant approvedAt,
                           Instant rejectedAt,
                           Instant archivedAt,
                           Status status) {
    public enum Status {
        AWAITING_APPROVAL, APPROVED, REJECTED, ARCHIVED,
    }
}
