package it.tarczynski.onion.library.book;

import it.tarczynski.onion.library.author.AuthorId;
import it.tarczynski.onion.library.shared.ApprovedAt;
import it.tarczynski.onion.library.shared.ArchivedAt;
import it.tarczynski.onion.library.shared.CreatedAt;
import it.tarczynski.onion.library.shared.RejectedAt;
import it.tarczynski.onion.library.shared.Title;
import it.tarczynski.onion.library.shared.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
abstract sealed class Book {

    @EqualsAndHashCode.Include
    protected final BookId id;
    protected final Version version;
    protected final AuthorId author;
    protected final Title title;
    protected final CreatedAt createdAt;
    protected final ApprovedAt approvedAt;
    protected final RejectedAt rejectedAt;
    protected final ArchivedAt archivedAt;

    private Book(BookId id, Version version, AuthorId author, Title title, CreatedAt createdAt) {
        this(id, version, author, title, createdAt, null, null, null);
    }

    private Book(NewBook book, ApprovedAt approvedAt) {
        this(book.id, book.version, book.author, book.title, book.createdAt, approvedAt, null, null);
    }

    private Book(NewBook book, RejectedAt rejectedAt) {
        this(book.id, book.version, book.author, book.title, book.createdAt, null, rejectedAt, null);
    }

    public Book(ApprovedBook book, ArchivedAt archivedAt) {
        this(book.id, book.version, book.author, book.title, book.createdAt, book.approvedAt, null, archivedAt);
    }

    public Book(RejectedBook book, ArchivedAt archivedAt) {
        this(book.id, book.version, book.author, book.title, book.createdAt, null, book.rejectedAt, archivedAt);
    }

    private Book(BookSnapshot snapshot) {
        this(
                snapshot.id(),
                snapshot.version(),
                snapshot.author(),
                snapshot.title(),
                snapshot.createdAt(),
                snapshot.approvedAt(),
                snapshot.rejectedAt(),
                snapshot.archivedAt()
        );
    }

    static Book create(AuthorId author, Title title, CreatedAt createdAt) {
        return new NewBook(BookId.next(), Version.first(), author, title, createdAt);
    }

    static Book from(BookSnapshot snapshot) {
        return switch (snapshot.status()) {
            case AWAITING_APPROVAL -> new NewBook(snapshot);
            case APPROVED -> new ApprovedBook(snapshot);
            case REJECTED -> new RejectedBook(snapshot);
            case ARCHIVED -> new ArchivedBook(snapshot);
        };
    }

    private static final class NewBook extends Book {

        private NewBook(BookId id, Version version, AuthorId author, Title title, CreatedAt createdAt) {
            super(id, version, author, title, createdAt);
        }

        public NewBook(BookSnapshot snapshot) {
            super(snapshot);
        }

        @Override
        protected BookSnapshot.Status status() {
            return BookSnapshot.Status.AWAITING_APPROVAL;
        }

        @Override
        Book approve(ApprovedAt approvedAt) {
            return new ApprovedBook(this, approvedAt);
        }

        @Override
        Book reject(RejectedAt rejectedAt) {
            return new RejectedBook(this, rejectedAt);
        }
    }

    private static final class ApprovedBook extends Book {

        private ApprovedBook(NewBook book, ApprovedAt approvedAt) {
            super(book, approvedAt);
        }

        public ApprovedBook(BookSnapshot snapshot) {
            super(snapshot);
        }

        @Override
        protected BookSnapshot.Status status() {
            return BookSnapshot.Status.APPROVED;
        }

        @Override
        Book approve(ApprovedAt approvedAt) {
            return this;
        }

        @Override
        Book archive(ArchivedAt archivedAt) {
            return new ArchivedBook(this, archivedAt);
        }
    }

    private static final class RejectedBook extends Book {

        private RejectedBook(NewBook book, RejectedAt rejectedAt) {
            super(book, rejectedAt);
        }

        public RejectedBook(BookSnapshot snapshot) {
            super(snapshot);
        }

        @Override
        protected BookSnapshot.Status status() {
            return BookSnapshot.Status.REJECTED;
        }

        @Override
        Book reject(RejectedAt rejectedAt) {
            return this;
        }

        @Override
        Book archive(ArchivedAt archivedAt) {
            return new ArchivedBook(this, archivedAt);
        }
    }

    private static final class ArchivedBook extends Book {


        private ArchivedBook(ApprovedBook book, ArchivedAt archivedAt) {
            super(book, archivedAt);
        }

        private ArchivedBook(RejectedBook book, ArchivedAt archivedAt) {
            super(book, archivedAt);
        }

        public ArchivedBook(BookSnapshot snapshot) {
            super(snapshot);
        }

        @Override
        protected BookSnapshot.Status status() {
            return BookSnapshot.Status.ARCHIVED;
        }
    }

    protected abstract BookSnapshot.Status status();

    Book approve(ApprovedAt approvedAt) {
        throw new UnsupportedOperationException("Unsupported state transition. Cannot approve book in state [%s]".formatted(status()));
    }

    Book reject(RejectedAt rejectedAt) {
        throw new UnsupportedOperationException("Unsupported state transition. Cannot reject book in state [%s]".formatted(status()));
    }

    Book archive(ArchivedAt archivedAt) {
        throw new UnsupportedOperationException("Unsupported state transition. Cannot archive book in state [%s]".formatted(status()));
    }

    BookSnapshot snapshot() {
        return BookSnapshot.builder()
                .id(id)
                .version(version)
                .author(author)
                .title(title)
                .createdAt(createdAt)
                .approvedAt(approvedAt)
                .rejectedAt(rejectedAt)
                .archivedAt(archivedAt)
                .status(status())
                .build();
    }
}
