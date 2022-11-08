package it.tarczynski.onion.library.book.web.command;

import it.tarczynski.onion.library.author.AuthorId;
import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.book.Books;
import it.tarczynski.onion.library.book.web.ApproveBookCommand;
import it.tarczynski.onion.library.book.web.ArchiveBookCommand;
import it.tarczynski.onion.library.book.web.CreateBookCommand;
import it.tarczynski.onion.library.book.web.RejectBookCommand;
import it.tarczynski.onion.library.shared.ApiV1;
import it.tarczynski.onion.library.shared.Title;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiV1("/books/commands")
@AllArgsConstructor
class BookCommandEndpoint {

    private final Books books;

    @PostMapping("/create")
    ResponseEntity<BookResponse> createBook(@RequestBody CreateBookCommand command) {
        final AuthorId author = AuthorId.from(command.getAuthor().getId());
        final Title title = new Title(command.getTitle());
        final BookResponse book = BookResponse.from(books.create(author, title));
        return ResponseEntity.accepted().body(book);
    }

    @PostMapping("/approve")
    ResponseEntity<BookResponse> approveBook(@RequestBody ApproveBookCommand command) {
        final BookId bookId = BookId.from(command.id());
        final BookResponse book = BookResponse.from(books.approve(bookId));
        return ResponseEntity.accepted().body(book);
    }

    @PostMapping("/reject")
    ResponseEntity<BookResponse> rejectBook(@RequestBody RejectBookCommand command) {
        final BookId bookId = BookId.from(command.id());
        final BookResponse book = BookResponse.from(books.reject(bookId));
        return ResponseEntity.accepted().body(book);
    }

    @PostMapping("/archive")
    ResponseEntity<BookResponse> archiveBook(@RequestBody ArchiveBookCommand command) {
        final BookId bookId = BookId.from(command.id());
        final BookResponse book = BookResponse.from(books.archive(bookId));
        return ResponseEntity.accepted().body(book);
    }
}
