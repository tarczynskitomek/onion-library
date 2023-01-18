package it.tarczynski.onion.library.book;

import java.util.UUID;

public record BookId(UUID value) {

    public static BookId from(String string) {
        return new BookId(UUID.fromString(string));
    }

    static BookId next() {
        return new BookId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
