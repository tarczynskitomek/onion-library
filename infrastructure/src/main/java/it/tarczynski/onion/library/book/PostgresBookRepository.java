package it.tarczynski.onion.library.book;

import it.tarczynski.onion.library.generated.tables.records.BooksRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;

import static it.tarczynski.onion.library.generated.tables.Books.BOOKS;
import static java.time.ZoneOffset.UTC;

@Component
@AllArgsConstructor
class PostgresBookRepository implements BookRepository {

    private final DSLContext dsl;

    @Override
    public Book create(Book book) {
        final BookSnapshot snapshot = book.snapshot();
        dsl.insertInto(BOOKS)
                .set(BOOKS.ID, snapshot.id())
                .set(BOOKS.AUTHOR, snapshot.authorId())
                .set(BOOKS.TITLE, snapshot.title())
                .set(BOOKS.CREATED_AT, toOffsetDateTimeNullable(snapshot.createdAt()))
                .set(BOOKS.APPROVED_AT, toOffsetDateTimeNullable(snapshot.approvedAt()))
                .set(BOOKS.REJECTED_AT, toOffsetDateTimeNullable(snapshot.rejectedAt()))
                .set(BOOKS.ARCHIVED_AT, toOffsetDateTimeNullable(snapshot.archivedAt()))
                .set(BOOKS.STATUS, snapshot.status().toString())
                .execute();
        return book;
    }

    @Override
    public Book getById(BookId id) {
        final BooksRecord record = dsl.fetchOne(BOOKS, BOOKS.ID.eq(id.value().toString()));
        if (record == null) {
            throw new IllegalStateException("Book doesn't exist - fixme should be dedicated exception");
        }
        final BookSnapshot snapshot = BookSnapshot.builder()
                .id(record.getId())
                .title(record.getTitle())
                .authorId(record.getAuthor())
                .createdAt(record.getCreatedAt().toInstant())
                .approvedAt(toInstantNullable(record.getApprovedAt()))
                .rejectedAt(toInstantNullable(record.getRejectedAt()))
                .archivedAt(toInstantNullable(record.getArchivedAt()))
                .status(BookSnapshot.Status.valueOf(record.getStatus()))
                .build();
        return Book.from(snapshot);
    }

    @Nullable
    private static Instant toInstantNullable(@Nullable OffsetDateTime offsetDateTime) {
        return offsetDateTime == null
                ? null
                : offsetDateTime.toInstant();
    }

    @Nullable
    private OffsetDateTime toOffsetDateTimeNullable(@Nullable Instant instant) {
        return instant == null
                ? null
                : instant.atOffset(UTC);
    }
}
