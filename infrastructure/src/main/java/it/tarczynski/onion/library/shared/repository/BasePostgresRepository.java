package it.tarczynski.onion.library.shared.repository;

import org.springframework.lang.Nullable;

import java.time.Instant;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;

public abstract class BasePostgresRepository {

    protected boolean updatedConcurrently(int updatedRecords) {
        return updatedRecords == 0;
    }

    @Nullable
    protected static Instant toInstantNullable(@Nullable OffsetDateTime offsetDateTime) {
        return offsetDateTime == null
                ? null
                : offsetDateTime.toInstant();
    }

    @Nullable
    protected OffsetDateTime toOffsetDateTimeNullable(@Nullable Instant instant) {
        return instant == null
                ? null
                : instant.atOffset(UTC);
    }
}
