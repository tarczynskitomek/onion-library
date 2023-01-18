package it.tarczynski.onion.library.shared;

import java.time.Instant;

public class CreatedAt extends TimeValue {

    public CreatedAt(Instant time) {
        super(time);
    }

    public static CreatedAt from(Instant time) {
        return time == null
                ? null
                : new CreatedAt(time);
    }
}
