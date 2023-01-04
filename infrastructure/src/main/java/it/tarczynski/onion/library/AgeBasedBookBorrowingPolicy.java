package it.tarczynski.onion.library;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.reader.ReaderId;
import it.tarczynski.onion.library.reader.ReaderQueryRepository;
import it.tarczynski.onion.library.reader.ReaderSnapshot;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class AgeBasedBookBorrowingPolicy implements BookBorrowingPolicy {

    private final ReaderQueryRepository readerQueryRepository;

    @Override
    public void verifyCanBorrow(BookId bookId, ReaderId readerId) {
        final ReaderSnapshot reader = readerQueryRepository.getBy(readerId);
        if (!reader.isAdult()) {
            throw new CannotBorrowException("Reader does not meet minimum age requirements");
        }
    }
}
