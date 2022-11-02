package it.tarczynski.onion.library.shared;

import java.time.Instant;

public record ApprovedAt(Instant time) {

    public static ApprovedAt from(Instant time) {
        return time == null
                ? null
                : new ApprovedAt(time);
    }
}
