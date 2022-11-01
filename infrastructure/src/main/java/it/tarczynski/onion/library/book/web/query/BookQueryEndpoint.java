package it.tarczynski.onion.library.book.web.query;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.book.BookQueryRepository;
import it.tarczynski.onion.library.book.BookSnapshot;
import it.tarczynski.onion.library.shared.ApiV1;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ApiV1("/books")
@AllArgsConstructor
class BookQueryEndpoint {

    private final BookQueryRepository bookQueryRepository;

    @GetMapping("/{id}")
    BookSnapshot queryBy(@PathVariable String id) {
        return bookQueryRepository.getBy(BookId.from(id));
    }
}
