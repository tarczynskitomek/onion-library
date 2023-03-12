package it.tarczynski.onion.library.book.web.command;

import it.tarczynski.onion.library.book.BookSnapshot;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.Instant;

@Builder(access = AccessLevel.PRIVATE)
public record BookResponse(String id,
                           Author author,
                           String title,
                           Instant createdAt,
                           String status) {

    public record Author(String id) {
    }

    public static BookResponse from(BookSnapshot snapshot) {
        return BookResponse.builder()
                .id(snapshot.id().value().toString())
                .author(new Author(snapshot.author().value().toString()))
                .title(snapshot.title().value())
                .createdAt(snapshot.createdAt().time())
                .status(snapshot.type().name())
                .build();
    }
}
