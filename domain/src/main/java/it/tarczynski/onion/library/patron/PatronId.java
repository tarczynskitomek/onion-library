package it.tarczynski.onion.library.patron;

import java.util.UUID;

public record PatronId(UUID value) {
    public static PatronId next() {
        return new PatronId(UUID.randomUUID());
    }

    public static PatronId from(String id) {
        return new PatronId(UUID.fromString(id));
    }
}
