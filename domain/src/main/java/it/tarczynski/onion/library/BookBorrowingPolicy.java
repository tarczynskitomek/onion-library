package it.tarczynski.onion.library;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.reader.ReaderId;

interface BookBorrowingPolicy {
    void verifyCanBorrow(BookId bookId, ReaderId readerId);
}
