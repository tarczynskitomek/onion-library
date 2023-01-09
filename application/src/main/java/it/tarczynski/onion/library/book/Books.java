package it.tarczynski.onion.library.book;

import it.tarczynski.onion.library.author.AuthorId;
import it.tarczynski.onion.library.shared.ApprovedAt;
import it.tarczynski.onion.library.shared.ArchivedAt;
import it.tarczynski.onion.library.shared.CreatedAt;
import it.tarczynski.onion.library.shared.RejectedAt;
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
            final CreatedAt createdAt = new CreatedAt(timeMachine.now());
            final Book book = Book.create(author, title, createdAt);
            return bookRepository.create(book).snapshot();
        });
    }

    public BookSnapshot handle(ApproveBookCommand command) {
        return transactions.execute(() -> {
            final BookId bookId = command.id();
            final Book book = bookRepository.getById(bookId);
            final ApprovedAt approvedAt = new ApprovedAt(timeMachine.now());
            final Book approved = book.approve(approvedAt);
            return bookRepository.update(approved).snapshot();
        });
    }

    public BookSnapshot handle(RejectBookCommand command) {
        return transactions.execute(() -> {
            final BookId bookId = command.id();
            final Book book = bookRepository.getById(bookId);
            final RejectedAt rejectedAt = new RejectedAt(timeMachine.now());
            final Book rejected = book.reject(rejectedAt);
            return bookRepository.update(rejected).snapshot();
        });
    }

    public BookSnapshot handle(ArchiveBookCommand command) {
        return transactions.execute(() -> {
            final BookId bookId = command.id();
            final Book book = bookRepository.getById(bookId);
            final ArchivedAt archivedAt = new ArchivedAt(timeMachine.now());
            final Book archived = book.archive(archivedAt);
            return bookRepository.update(archived).snapshot();
        });
    }
}
