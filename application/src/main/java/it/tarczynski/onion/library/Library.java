package it.tarczynski.onion.library;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.reader.ReaderId;
import it.tarczynski.onion.library.shared.Transactions;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Library {

    private final LoanRepository loanRepository;
    private final Transactions transactions;
    private final BookBorrowingPolicy bookBorrowingPolicy;

    public BookLoanSnapshot borrow(BorrowBookCommand command) {
        return transactions.execute(() -> {
            final BookId bookId = command.bookId();
            final ReaderId readerId = command.readerId();
            bookBorrowingPolicy.verifyCanBorrow(bookId, readerId);
            final BookLoan loan = BookLoan.create(bookId, readerId);
            return loanRepository.save(loan).snapshot();
        });
    }
}
