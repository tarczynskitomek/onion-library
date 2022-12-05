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

import java.util.Optional;

@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
abstract sealed class Book {

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

    private Book(BookId id, Version version, AuthorId author, Title title, CreatedAt createdAt, ApprovedAt approvedAt) {
        this(id, version, author, title, createdAt, approvedAt, null, null);
    }

    private Book(BookId id, Version version, AuthorId author, Title title, CreatedAt createdAt, ApprovedAt approvedAt, RejectedAt rejectedAt) {
        this(id, version, author, title, createdAt, approvedAt, rejectedAt, null);
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
            super(
                    BookId.from(snapshot.id()),
                    Version.from(snapshot.version()),
                    AuthorId.from(snapshot.author().id()),
                    new Title(snapshot.title()),
                    CreatedAt.from(snapshot.createdAt())
            );
        }

        @Override
        protected BookSnapshot.Status status() {
            return BookSnapshot.Status.AWAITING_APPROVAL;
        }

        @Override
        Book approve(ApprovedAt approvedAt) {
            return new ApprovedBook(id, version, author, title, createdAt, approvedAt);
        }

        @Override
        Book reject(RejectedAt rejectedAt) {
            return new RejectedBook(id, version, author, title, createdAt, rejectedAt);
        }
    }

    private static final class ApprovedBook extends Book {

        private ApprovedBook(BookId id, Version version, AuthorId author, Title title, CreatedAt createdAt, ApprovedAt approvedAt) {
            super(id, version, author, title, createdAt, approvedAt);
        }

        public ApprovedBook(BookSnapshot snapshot) {
            super(
                    BookId.from(snapshot.id()),
                    Version.from(snapshot.version()),
                    AuthorId.from(snapshot.author().id()),
                    new Title(snapshot.title()),
                    CreatedAt.from(snapshot.createdAt()),
                    ApprovedAt.from(snapshot.approvedAt()));
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
            return new ArchivedBook(id, version, author, title, createdAt, approvedAt, archivedAt);
        }
    }

    private static final class RejectedBook extends Book {

        private RejectedBook(BookId id, Version version, AuthorId author, Title title, CreatedAt createdAt, RejectedAt rejectedAt) {
            super(id, version, author, title, createdAt, null, rejectedAt);
        }

        public RejectedBook(BookSnapshot snapshot) {
            super(
                    BookId.from(snapshot.id()),
                    Version.from(snapshot.version()),
                    AuthorId.from(snapshot.author().id()),
                    new Title(snapshot.title()),
                    CreatedAt.from(snapshot.createdAt()),
                    null,
                    RejectedAt.from(snapshot.rejectedAt())
            );
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
            return new ArchivedBook(id, version, author, title, createdAt, rejectedAt, archivedAt);
        }
    }

    private static final class ArchivedBook extends Book {


        private ArchivedBook(BookId id, Version version, AuthorId author, Title title, CreatedAt createdAt, ApprovedAt approvedAt, ArchivedAt archivedAt) {
            super(id, version, author, title, createdAt, approvedAt, null, archivedAt);
        }

        private ArchivedBook(BookId id, Version version, AuthorId author, Title title, CreatedAt createdAt, RejectedAt rejectedAt, ArchivedAt archivedAt) {
            super(id, version, author, title, createdAt, null, rejectedAt, archivedAt);
        }

        public ArchivedBook(BookSnapshot snapshot) {
            super(
                    BookId.from(snapshot.id()),
                    Version.from(snapshot.version()),
                    AuthorId.from(snapshot.author().id()),
                    new Title(snapshot.title()),
                    CreatedAt.from(snapshot.createdAt()),
                    null, RejectedAt.from(snapshot.rejectedAt()),
                    ArchivedAt.from(snapshot.archivedAt())
            );
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

    private Optional<ApprovedAt> approvedAt() {
        return Optional.ofNullable(approvedAt);
    }

    private Optional<RejectedAt> rejectedAt() {
        return Optional.ofNullable(rejectedAt);
    }

    private Optional<ArchivedAt> archivedAt() {
        return Optional.ofNullable(archivedAt);
    }

    BookSnapshot snapshot() {
        return BookSnapshot.builder()
                .id(id.value().toString())
                .version(version.value())
                .author(new BookSnapshot.Author(author.value().toString()))
                .title(title.value())
                .createdAt(createdAt.time())
                .approvedAt(approvedAt().map(ApprovedAt::time).orElse(null))
                .rejectedAt(rejectedAt().map(RejectedAt::time).orElse(null))
                .archivedAt(archivedAt().map(ArchivedAt::time).orElse(null))
                .status(status())
                .build();
    }
}
