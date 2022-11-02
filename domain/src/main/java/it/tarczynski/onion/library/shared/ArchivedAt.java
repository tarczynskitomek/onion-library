package it.tarczynski.onion.library.shared;

import java.time.Instant;

public record ArchivedAt(Instant time) {

    public static ArchivedAt from(Instant time) {
        return time == null
                ? null
                : new ArchivedAt(time);
    }
}
