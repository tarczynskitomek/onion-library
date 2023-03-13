package it.tarczynski.onion.library.book.web.command;

import it.tarczynski.onion.library.book.Books;
import it.tarczynski.onion.library.book.CreateBookCommand;
import it.tarczynski.onion.library.shared.ApiV1;
import jakarta.validation.Valid;
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
            @RequestBody @Valid CreateBookCommand command,
            @PathVariable("id") String commandId
    ) {
        LOG.info("[{}] Consumed command [{}]", commandId, command);
        final BookResponse book = BookResponse.from(books.handle(command));
        return ResponseEntity.accepted().body(book);
    }
}
