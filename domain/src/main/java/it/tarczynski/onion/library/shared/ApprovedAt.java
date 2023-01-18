package it.tarczynski.onion.library.shared;

import java.time.Instant;

public class ApprovedAt extends TimeValue {

    public ApprovedAt(Instant time) {
        super(time);
    }

    public static ApprovedAt from(Instant time) {
        return time == null
                ? null
                : new ApprovedAt(time);
    }
}
