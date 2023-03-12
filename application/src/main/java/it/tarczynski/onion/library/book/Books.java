package it.tarczynski.onion.library.book;

import it.tarczynski.onion.library.author.AuthorId;
import it.tarczynski.onion.library.shared.CreatedAt;
import it.tarczynski.onion.library.shared.TimeMachine;
import it.tarczynski.onion.library.shared.Title;
import it.tarczynski.onion.library.shared.Transactions;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Books {

    private final BookRepository bookRepository;
    private final TimeMachine timeMachine;
    private final Transactions transactions;

    public BookSnapshot handle(CreateBookCommand command) {
        return transactions.execute(() -> {
            final Title title = command.title();
            final AuthorId author = command.author();
            final BookType type = command.type();
            final CreatedAt createdAt = new CreatedAt(timeMachine.now());
            final Book book = Book.create(type, author, title, createdAt);
            return bookRepository.create(book).snapshot();
        });
    }
}
