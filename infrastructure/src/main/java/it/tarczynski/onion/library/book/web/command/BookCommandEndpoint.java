package it.tarczynski.onion.library.book.web.command;

import it.tarczynski.onion.library.author.AuthorId;
import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.book.BookSnapshot;
import it.tarczynski.onion.library.book.Books;
import it.tarczynski.onion.library.book.web.ApproveBookCommand;
import it.tarczynski.onion.library.book.web.CreateBookCommand;
import it.tarczynski.onion.library.book.web.RejectBookCommand;
import it.tarczynski.onion.library.shared.ApiV1;
import it.tarczynski.onion.library.shared.Title;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.ACCEPTED;

@ApiV1("/books/commands")
@AllArgsConstructor
class BookCommandEndpoint {

    private final Books books;

    @PostMapping("/create")
    @ResponseStatus(ACCEPTED)
    ResponseEntity<BookSnapshot> createBook(@RequestBody CreateBookCommand command) {
        final AuthorId author = AuthorId.from(command.getAuthor().getId());
        final Title title = new Title(command.getTitle());
        final BookSnapshot book = books.create(author, title);
        return ResponseEntity.accepted().body(book);
    }

    @PostMapping("/approve")
    @ResponseStatus(ACCEPTED)
    ResponseEntity<BookSnapshot> approveBook(@RequestBody ApproveBookCommand command) {
        final BookId bookId = BookId.from(command.id());
        final BookSnapshot book = books.approve(bookId);
        return ResponseEntity.accepted().body(book);
    }

    @PostMapping("/reject")
    @ResponseStatus(ACCEPTED)
    ResponseEntity<BookSnapshot> rejectBook(@RequestBody RejectBookCommand command) {
        final BookId bookId = BookId.from(command.id());
        final BookSnapshot book = books.reject(bookId);
        return ResponseEntity.accepted().body(book);
    }
}
