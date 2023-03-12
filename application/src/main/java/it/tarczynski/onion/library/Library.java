package it.tarczynski.onion.library;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.patron.PatronId;
import it.tarczynski.onion.library.shared.Transactions;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class Library {

    private final HoldRepository holdRepository;
    private final Transactions transactions;
    private final BookHoldingPolicy bookHoldingPolicy;

    public BookHoldSnapshot handle(HoldBookCommand command) {
        return transactions.execute(() -> {
            final BookId bookId = command.bookId();
            final PatronId patronId = command.patronId();
            bookHoldingPolicy.verifyCanBorrow(bookId, patronId);
            final BookHold hold = BookHold.create(bookId, patronId);
            return holdRepository.create(hold).snapshot();
        });
    }
}
