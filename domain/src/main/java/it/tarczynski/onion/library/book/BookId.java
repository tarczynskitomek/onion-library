package it.tarczynski.onion.library.book;

import java.util.UUID;

public record BookId(UUID value) {

    static BookId from(String string) {
        return new BookId(UUID.fromString(string));
    }

    static BookId next() {
        return new BookId(UUID.randomUUID());
    }
}
