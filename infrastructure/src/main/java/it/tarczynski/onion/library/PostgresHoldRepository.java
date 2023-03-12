package it.tarczynski.onion.library;

import it.tarczynski.onion.library.shared.repository.BasePostgresRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import static it.tarczynski.onion.library.generated.tables.Holds.HOLDS;

@Primary
@Repository
@AllArgsConstructor
// jOOQ operations implement AutoCloseable interface, but the resources are closed eagerly - no need to do that by the client code
@SuppressWarnings("resource")
public class PostgresHoldRepository extends BasePostgresRepository implements HoldRepository {

    private final DSLContext dsl;

    @Override
    public BookHold create(BookHold hold) {
        final BookHoldSnapshot snapshot = hold.snapshot();
        dsl.insertInto(HOLDS)
                .set(HOLDS.ID, snapshot.id())
                .set(HOLDS.BOOK_ID, snapshot.bookId())
                .set(HOLDS.PATRON_ID, snapshot.patronId())
                .execute();
        return hold;
    }
}
