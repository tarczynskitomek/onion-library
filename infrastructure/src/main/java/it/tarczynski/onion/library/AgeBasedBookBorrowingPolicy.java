package it.tarczynski.onion.library;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.patron.PatronId;
import it.tarczynski.onion.library.patron.PatronQueryRepository;
import it.tarczynski.onion.library.patron.PatronSnapshot;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class AgeBasedBookBorrowingPolicy implements BookBorrowingPolicy {

    private final PatronQueryRepository patronQueryRepository;

    @Override
    public void verifyCanBorrow(BookId bookId, PatronId patronId) {
        final PatronSnapshot patron = patronQueryRepository.getBy(patronId);
        if (!patron.isAdult()) {
            throw new CannotBorrowException("Patron does not meet minimum age requirements");
        }
    }
}
