package it.tarczynski.onion.library.shared;

import java.time.Instant;

public record RejectedAt(Instant time) {

    public static RejectedAt from(Instant time) {
        return time == null
                ? null
                : new RejectedAt(time);
    }
}
