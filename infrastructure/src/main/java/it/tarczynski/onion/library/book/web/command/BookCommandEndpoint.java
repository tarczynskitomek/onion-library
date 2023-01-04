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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiV1("/books/commands")
@AllArgsConstructor
class BookCommandEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(BookCommandEndpoint.class);
    private final Books books;

    @PutMapping("/create/{id}")
    ResponseEntity<BookResponse> createBook(
            @RequestBody CreateBookCommand command,
            @PathVariable("id") String commandId
    ) {
        LOG.info("[{}] Consumed command [{}]", commandId, command);
        final AuthorId author = AuthorId.from(command.getAuthor().getId());
        final Title title = new Title(command.getTitle());
        final BookResponse book = BookResponse.from(books.create(author, title));
        return ResponseEntity.accepted().body(book);
    }

    @PutMapping("/approve/{id}")
    ResponseEntity<BookResponse> approveBook(
            @RequestBody ApproveBookCommand command,
            @PathVariable("id") String commandId
    ) {
        LOG.info("[{}] Consumed command [{}]", commandId, command);
        final BookId bookId = BookId.from(command.id());
        final BookResponse book = BookResponse.from(books.approve(bookId));
        return ResponseEntity.accepted().body(book);
    }

    @PutMapping("/reject/{id}")
    ResponseEntity<BookResponse> rejectBook(
            @RequestBody RejectBookCommand command,
            @PathVariable("id") String commandId
    ) {
        LOG.info("[{}] Consumed command [{}]", commandId, command);
        final BookId bookId = BookId.from(command.id());
        final BookResponse book = BookResponse.from(books.reject(bookId));
        return ResponseEntity.accepted().body(book);
    }

    @PutMapping("/archive/{id}")
    ResponseEntity<BookResponse> archiveBook(
            @RequestBody ArchiveBookCommand command,
            @PathVariable("id") String commandId
    ) {
        LOG.info("[{}] Consumed command [{}]", commandId, command);
        final BookId bookId = BookId.from(command.id());
        final BookResponse book = BookResponse.from(books.archive(bookId));
        return ResponseEntity.accepted().body(book);
    }
}
