package it.tarczynski.onion.library.shared;

import java.time.Instant;

public class ArchivedAt extends TimeValue {

    public ArchivedAt(Instant time) {
        super(time);
    }

    public static ArchivedAt from(Instant time) {
        return time == null
                ? null
                : new ArchivedAt(time);
    }
}
