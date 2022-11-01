package it.tarczynski.onion.library.author;

import java.util.UUID;

public record AuthorId(UUID value) {

    public static AuthorId next() {
        return new AuthorId(UUID.randomUUID());
    }

    public static AuthorId from(String string) {
        return new AuthorId(UUID.fromString(string));
    }
}
