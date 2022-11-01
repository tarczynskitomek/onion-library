package it.tarczynski.onion.library.book.web.command;

import it.tarczynski.onion.library.author.AuthorId;
import it.tarczynski.onion.library.book.BookSnapshot;
import it.tarczynski.onion.library.book.Books;
import it.tarczynski.onion.library.book.web.CreateBookCommand;
import it.tarczynski.onion.library.shared.ApiV1;
import it.tarczynski.onion.library.shared.Title;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ApiV1("/books/commands")
@AllArgsConstructor
class BookCommandEndpoint {

    private final Books books;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<BookSnapshot> createBook(@RequestBody CreateBookCommand command) {
        final AuthorId author = AuthorId.from(command.getAuthor().getId());
        final Title title = new Title(command.getTitle());
        final BookSnapshot book = books.create(author, title);
        return ResponseEntity.accepted().body(book);
    }
}
