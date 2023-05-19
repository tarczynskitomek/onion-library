package it.tarczynski.onion.library.borrowing;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.patron.PatronId;

interface BookHoldingPolicy {
    void verifyCanHold(BookId bookId, PatronId patronId);
}
