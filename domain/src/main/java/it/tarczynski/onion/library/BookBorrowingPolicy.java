package it.tarczynski.onion.library;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.patron.PatronId;

interface BookBorrowingPolicy {
    void verifyCanBorrow(BookId bookId, PatronId patronId);
}
