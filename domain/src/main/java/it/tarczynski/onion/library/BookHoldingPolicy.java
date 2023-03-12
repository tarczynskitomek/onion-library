package it.tarczynski.onion.library;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.patron.PatronId;

interface BookHoldingPolicy {
    void verifyCanBorrow(BookId bookId, PatronId patronId);
}
