package it.tarczynski.onion.library.shared;

import java.time.Instant;

public class RejectedAt extends TimeValue {

    public RejectedAt(Instant time) {
        super(time);
    }

    public static RejectedAt from(Instant time) {
        return time == null
                ? null
                : new RejectedAt(time);
    }
}
