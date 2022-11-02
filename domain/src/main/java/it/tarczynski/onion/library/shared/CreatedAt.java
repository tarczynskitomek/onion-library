package it.tarczynski.onion.library.shared;

import java.time.Instant;

public record CreatedAt(Instant time) {

    public static CreatedAt from(Instant time) {
        return time == null
                ? null
                : new CreatedAt(time);
    }
}
