package it.tarczynski.onion.library.patron;

import java.util.HashMap;
import java.util.Map;

interface PatronRepository {

    Patron create(Patron patron);

    Patron getBy(PatronId id);

    class InMemoryPatronRepository implements PatronRepository {

        private final Map<PatronId, Patron> patrons = new HashMap<>();

        @Override
        public Patron create(Patron patron) {
            patrons.put(patron.snapshot().id(), patron);
            return patron;
        }

        @Override
        public Patron getBy(PatronId id) {
            return patrons.get(id);
        }
    }
}
