package it.tarczynski.onion.library.book;

import it.tarczynski.onion.library.author.AuthorId;
import it.tarczynski.onion.library.generated.tables.records.BooksRecord;
import it.tarczynski.onion.library.shared.ApprovedAt;
import it.tarczynski.onion.library.shared.ArchivedAt;
import it.tarczynski.onion.library.shared.CreatedAt;
import it.tarczynski.onion.library.shared.RejectedAt;
import it.tarczynski.onion.library.shared.Title;
import it.tarczynski.onion.library.shared.Version;
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
                .set(BOOKS.ID, snapshot.id().value().toString())
                .set(BOOKS.VERSION, snapshot.version().value())
                .set(BOOKS.AUTHOR, snapshot.author().value().toString())
                .set(BOOKS.TITLE, snapshot.title().value())
                .set(BOOKS.CREATED_AT, snapshot.createdAtOffsetTime())
                .set(BOOKS.APPROVED_AT, snapshot.approvedAtOffsetTime())
                .set(BOOKS.REJECTED_AT, snapshot.rejectedAtOffsetTime())
                .set(BOOKS.ARCHIVED_AT, snapshot.archivedAtOffsetTime())
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
                .id(BookId.from(record.getId()))
                .version(Version.from(record.getVersion()))
                .title(Title.of(record.getTitle()))
                .author(AuthorId.from(record.getAuthor()))
                .createdAt(CreatedAt.from(record.getCreatedAt().toInstant()))
                .approvedAt(ApprovedAt.from(toInstantNullable(record.getApprovedAt())))
                .rejectedAt(RejectedAt.from(toInstantNullable(record.getRejectedAt())))
                .archivedAt(ArchivedAt.from(toInstantNullable(record.getArchivedAt())))
                .status(BookSnapshot.Status.valueOf(record.getStatus()))
                .build();
        return Book.from(snapshot);
    }

    @Override
    public Book update(Book book) {
        final BookSnapshot snapshot = book.snapshot();
        final int updatedRecords = executeUpdate(snapshot);
        if (updatedConcurrently(updatedRecords)) {
            throw new OptimisticLockingException("Book [%s] has been update concurrently".formatted(book.snapshot().id()));
        }
        return book;
    }

    private int executeUpdate(BookSnapshot snapshot) {
        return dsl.update(BOOKS)
                .set(BOOKS.VERSION, snapshot.version().value() + 1)
                .set(BOOKS.STATUS, snapshot.status().toString())
                .set(BOOKS.APPROVED_AT, snapshot.approvedAtOffsetTime())
                .set(BOOKS.REJECTED_AT, snapshot.rejectedAtOffsetTime())
                .set(BOOKS.ARCHIVED_AT, snapshot.archivedAtOffsetTime())
                .where(
                        BOOKS.ID.eq(snapshot.id().value().toString())
                                .and(BOOKS.VERSION.eq(snapshot.version().value()))
                )
                .execute();
    }
}
