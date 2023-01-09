package it.tarczynski.onion.library;

import it.tarczynski.onion.library.shared.repository.BasePostgresRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import static it.tarczynski.onion.library.generated.tables.Loans.LOANS;

@Primary
@Repository
@AllArgsConstructor
// jOOQ operations implement AutoCloseable interface, but the resources are closed eagerly - no need to do that by the client code
@SuppressWarnings("resource")
public class PostgresLoanRepository extends BasePostgresRepository implements LoanRepository {

    private final DSLContext dsl;

    @Override
    public BookLoan create(BookLoan loan) {
        final BookLoanSnapshot snapshot = loan.snapshot();
        dsl.insertInto(LOANS)
                .set(LOANS.ID, snapshot.id())
                .set(LOANS.BOOK_ID, snapshot.bookId())
                .set(LOANS.READER_ID, snapshot.readerId())
                .execute();
        return loan;
    }
}
