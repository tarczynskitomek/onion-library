package it.tarczynski.onion.library.shared;

import java.time.Instant;

public interface TimeMachine {

    default Instant now() {
        return Instant.now();
    }
}
