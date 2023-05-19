package it.tarczynski.onion.library.borrowing;

import java.util.HashMap;
import java.util.Map;

interface HoldRepository {
    BookHold create(BookHold hold);

    class InMemoryHoldRepository implements HoldRepository {

        private final Map<String, BookHold> holds = new HashMap<>();

        @Override
        public BookHold create(BookHold hold) {
            this.holds.put(hold.snapshot().id(), hold);
            return hold;
        }
    }
}
