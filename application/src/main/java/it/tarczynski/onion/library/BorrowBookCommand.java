package it.tarczynski.onion.library;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.reader.ReaderId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BorrowBookCommand {

    private String bookId;
    private String readerId;

    BookId bookId() {
        return BookId.from(bookId);
    }

    ReaderId readerId() {
        return ReaderId.from(readerId);
    }
}
