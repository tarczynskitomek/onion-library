package it.tarczynski.onion.library.book;

import lombok.Builder;

import java.time.Instant;

@Builder
public record BookSnapshot(String id,
                           Integer version,
                           Author author,
                           String title,
                           Instant createdAt,
                           Instant approvedAt,
                           Instant rejectedAt,
                           Instant archivedAt,
                           Status status) {

    public record Author(String id) {
    }

    public enum Status {
        AWAITING_APPROVAL, APPROVED, REJECTED, ARCHIVED,
    }
}
