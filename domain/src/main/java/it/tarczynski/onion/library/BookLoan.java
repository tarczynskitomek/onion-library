package it.tarczynski.onion.library;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.reader.ReaderId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
class BookLoan {

    private final LoanId id;
    private final BookId bookId;
    private final ReaderId readerId;

    static BookLoan create(BookId bookId, ReaderId readerId) {
        return new BookLoan(LoanId.next(), bookId, readerId);
    }

    public BookLoanSnapshot snapshot() {
        return BookLoanSnapshot.builder()
                .id(id.value().toString())
                .bookId(bookId.value().toString())
                .readerId(readerId.value().toString())
                .build();
    }
}
