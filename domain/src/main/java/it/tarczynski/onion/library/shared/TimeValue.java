package it.tarczynski.onion.library.shared;

import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;

@AllArgsConstructor
public class TimeValue {

    private final Instant time;

    public OffsetDateTime toOffsetDateTime() {
        return time.atOffset(UTC);
    }

    public Instant time() {
        return time;
    }
}
