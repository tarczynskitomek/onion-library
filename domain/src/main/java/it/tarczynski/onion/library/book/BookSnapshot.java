package it.tarczynski.onion.library.book;

import it.tarczynski.onion.library.author.AuthorId;
import it.tarczynski.onion.library.shared.ApprovedAt;
import it.tarczynski.onion.library.shared.ArchivedAt;
import it.tarczynski.onion.library.shared.CreatedAt;
import it.tarczynski.onion.library.shared.RejectedAt;
import it.tarczynski.onion.library.shared.TimeValue;
import it.tarczynski.onion.library.shared.Title;
import it.tarczynski.onion.library.shared.Version;
import lombok.Builder;

import java.time.Instant;
import java.time.OffsetDateTime;

@Builder
public record BookSnapshot(BookId id,
                           Version version,
                           AuthorId author,
                           Title title,
                           CreatedAt createdAt,
                           ApprovedAt approvedAt,
                           RejectedAt rejectedAt,
                           ArchivedAt archivedAt,
                           Status status) {

    OffsetDateTime createdAtOffsetTime() {
        return createdAt.toOffsetDateTime();
    }

    OffsetDateTime approvedAtOffsetTime() {
        return toNullableOffsetDateTime(approvedAt);
    }

    OffsetDateTime rejectedAtOffsetTime() {
        return toNullableOffsetDateTime(rejectedAt);
    }

    OffsetDateTime archivedAtOffsetTime() {
        return toNullableOffsetDateTime(archivedAt);
    }

    private OffsetDateTime toNullableOffsetDateTime(TimeValue timeValue) {
        return timeValue != null ? timeValue.toOffsetDateTime() : null;
    }

    public Instant archivedAtTimeNullable() {
        return toNullableInstant(archivedAt);
    }

    public Instant approvedAtTimeNullable() {
        return toNullableInstant(approvedAt);
    }

    public Instant rejectedAtTimeNullable() {
        return toNullableInstant(rejectedAt);
    }

    private Instant toNullableInstant(TimeValue timeValue) {
        return timeValue != null ? timeValue.time() : null;
    }

    public enum Status {
        AWAITING_APPROVAL, APPROVED, REJECTED, ARCHIVED,
    }
}
