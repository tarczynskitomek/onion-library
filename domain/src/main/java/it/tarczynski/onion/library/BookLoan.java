package it.tarczynski.onion.library;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.patron.PatronId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
class BookLoan {

    private final LoanId id;
    private final BookId bookId;
    private final PatronId patronId;

    static BookLoan create(BookId bookId, PatronId patronId) {
        return new BookLoan(LoanId.next(), bookId, patronId);
    }

    public BookLoanSnapshot snapshot() {
        return BookLoanSnapshot.builder()
                .id(id.value().toString())
                .bookId(bookId.value().toString())
                .patronId(patronId.value().toString())
                .build();
    }
}
