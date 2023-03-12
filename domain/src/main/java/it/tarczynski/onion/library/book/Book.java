package it.tarczynski.onion.library.book;

import it.tarczynski.onion.library.author.AuthorId;
import it.tarczynski.onion.library.shared.CreatedAt;
import it.tarczynski.onion.library.shared.Title;
import it.tarczynski.onion.library.shared.Version;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
abstract sealed class Book {

    @EqualsAndHashCode.Include
    protected final BookId id;
    protected final Version version;
    protected final AuthorId author;
    protected final Title title;
    protected final CreatedAt createdAt;

    private Book(BookId id, Version version, AuthorId author, Title title, CreatedAt createdAt) {
        checkArgument(nonNull(id), "Id cannot be null");
        checkArgument(nonNull(version), "Version cannot be null");
        checkArgument(nonNull(author), "Author cannot be null");
        checkArgument(nonNull(title), "Title cannot be null");
        checkArgument(nonNull(createdAt), "Created at cannot be null");
        this.id = id;
        this.version = version;
        this.author = author;
        this.title = title;
        this.createdAt = createdAt;
    }

    static Book create(BookType type, AuthorId author, Title title, CreatedAt createdAt) {
        return switch (type) {
            case RESTRICTED -> new RestrictedBook(BookId.next(), Version.first(), author, title, createdAt);
            case CIRCULATING -> new CirculatingBook(BookId.next(), Version.first(), author, title, createdAt);
        };
    }

    static final class RestrictedBook extends Book {

        @Builder(access = AccessLevel.PACKAGE)
        RestrictedBook(BookId id, Version version, AuthorId author, Title title, CreatedAt createdAt) {
            super(id, version, author, title, createdAt);
        }

        @Override
        protected BookType type() {
            return BookType.RESTRICTED;
        }
    }

    static final class CirculatingBook extends Book {

        @Builder(access = AccessLevel.PACKAGE)
        CirculatingBook(BookId id, Version version, AuthorId author, Title title, CreatedAt createdAt) {
            super(id, version, author, title, createdAt);
        }

        @Override
        protected BookType type() {
            return BookType.CIRCULATING;
        }
    }

    protected abstract BookType type();

    BookSnapshot snapshot() {
        return BookSnapshot.builder()
                .id(id)
                .version(version)
                .author(author)
                .title(title)
                .createdAt(createdAt)
                .type(type())
                .build();
    }
}
