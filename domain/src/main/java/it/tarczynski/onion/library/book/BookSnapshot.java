package it.tarczynski.onion.library.book;

import it.tarczynski.onion.library.author.AuthorId;
import it.tarczynski.onion.library.shared.CreatedAt;
import it.tarczynski.onion.library.shared.Title;
import it.tarczynski.onion.library.shared.Version;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record BookSnapshot(BookId id,
                           Version version,
                           AuthorId author,
                           Title title,
                           CreatedAt createdAt,
                           BookType type) {

    OffsetDateTime createdAtOffsetTime() {
        return createdAt.toOffsetDateTime();
    }

    Book toDomain() {
        return switch (type) {
            case RESTRICTED -> new Book.RestrictedBook(id, version, author, title, createdAt);
            case CIRCULATING -> new Book.CirculatingBook(id, version, author, title, createdAt);
        };
    }
}
