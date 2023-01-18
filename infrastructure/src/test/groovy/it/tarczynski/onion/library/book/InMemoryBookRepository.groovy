package it.tarczynski.onion.library.book

class InMemoryBookRepository implements BookRepository {

    private final Map<BookId, Book> database = new HashMap<>()

    @Override
    Book create(Book book) {
        database.put(book.snapshot().id(), book)
        book
    }

    @Override
    Book getById(BookId id) {
        database.get(id)
    }

    @Override
    Book update(Book book) {
        database.put(book.snapshot().id(), book)
        book
    }
}
