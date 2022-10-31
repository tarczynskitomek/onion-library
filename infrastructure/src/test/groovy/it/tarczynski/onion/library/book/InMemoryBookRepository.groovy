package it.tarczynski.onion.library.book

class InMemoryBookRepository implements BookRepository {

    private final Map<String, Book> database = new HashMap<>()

    @Override
    Book save(Book book) {
        database.put(book.snapshot().id(), book)
        book
    }

    @Override
    Book getById(BookId id) {
        database.get(id.value().toString())
    }
}
