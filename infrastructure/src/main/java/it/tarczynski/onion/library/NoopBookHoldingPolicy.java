package it.tarczynski.onion.library;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.patron.PatronId;
import it.tarczynski.onion.library.patron.PatronQueryRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class NoopBookHoldingPolicy implements BookHoldingPolicy {

    private final PatronQueryRepository patronQueryRepository;

    @Override
    public void verifyCanBorrow(BookId bookId, PatronId patronId) {
        // no-op for the moment
    }
}
