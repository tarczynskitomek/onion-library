package it.tarczynski.onion.library.book;

import it.tarczynski.onion.library.generated.tables.records.BooksRecord;
import it.tarczynski.onion.library.shared.exception.OptimisticLockingException;
import it.tarczynski.onion.library.shared.exception.ResourceNotFound;
import it.tarczynski.onion.library.shared.repository.BasePostgresRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import static it.tarczynski.onion.library.generated.tables.Books.BOOKS;

@Component
@AllArgsConstructor
// jOOQ operations implement AutoCloseable interface, but the resources are closed eagerly - no need to do that by the client code
@SuppressWarnings("resource")
class PostgresBookRepository extends BasePostgresRepository implements BookRepository {

    private final DSLContext dsl;

    @Override
    public Book create(Book book) {
        final BookSnapshot snapshot = book.snapshot();
        dsl.insertInto(BOOKS)
                .set(BOOKS.ID, snapshot.id())
                .set(BOOKS.VERSION, snapshot.version())
                .set(BOOKS.AUTHOR, snapshot.author().id())
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
            throw new ResourceNotFound("Requested book [%s] does not exist".formatted(id.value()));
        }
        final BookSnapshot snapshot = BookSnapshot.builder()
                .id(record.getId())
                .version(record.getVersion())
                .title(record.getTitle())
                .author(new BookSnapshot.Author(record.getAuthor()))
                .createdAt(record.getCreatedAt().toInstant())
                .approvedAt(toInstantNullable(record.getApprovedAt()))
                .rejectedAt(toInstantNullable(record.getRejectedAt()))
                .archivedAt(toInstantNullable(record.getArchivedAt()))
                .status(BookSnapshot.Status.valueOf(record.getStatus()))
                .build();
        return Book.from(snapshot);
    }

    @Override
    public Book update(Book book) {
        final BookSnapshot snapshot = book.snapshot();
        final int updatedRecords = executeUpdate(snapshot);
        if (updatedConcurrently(updatedRecords)) {
            throw new OptimisticLockingException("Book [%s] has been update concurrently".formatted(book.id.value()));
        }
        return book;
    }

    private int executeUpdate(BookSnapshot snapshot) {
        return dsl.update(BOOKS)
                .set(BOOKS.VERSION, snapshot.version() + 1)
                .set(BOOKS.STATUS, snapshot.status().toString())
                .set(BOOKS.APPROVED_AT, toOffsetDateTimeNullable(snapshot.approvedAt()))
                .set(BOOKS.REJECTED_AT, toOffsetDateTimeNullable(snapshot.rejectedAt()))
                .set(BOOKS.ARCHIVED_AT, toOffsetDateTimeNullable(snapshot.archivedAt()))
                .where(
                        BOOKS.ID.eq(snapshot.id())
                                .and(BOOKS.VERSION.eq(snapshot.version()))
                )
                .execute();
    }
}
