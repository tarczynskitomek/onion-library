package it.tarczynski.onion.library;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.patron.PatronId;
import it.tarczynski.onion.library.shared.Transactions;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Library {

    private final LoanRepository loanRepository;
    private final Transactions transactions;
    private final BookBorrowingPolicy bookBorrowingPolicy;

    public BookLoanSnapshot handle(BorrowBookCommand command) {
        return transactions.execute(() -> {
            final BookId bookId = command.bookId();
            final PatronId patronId = command.patronId();
            bookBorrowingPolicy.verifyCanBorrow(bookId, patronId);
            final BookLoan loan = BookLoan.create(bookId, patronId);
            return loanRepository.create(loan).snapshot();
        });
    }
}
