package it.tarczynski.onion.library.reader;

import java.util.UUID;

public record ReaderId(UUID value) {
    public static ReaderId next() {
        return new ReaderId(UUID.randomUUID());
    }

    public static ReaderId from(String id) {
        return new ReaderId(UUID.fromString(id));
    }
}
