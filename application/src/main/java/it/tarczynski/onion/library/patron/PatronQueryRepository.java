package it.tarczynski.onion.library.patron;

import lombok.AllArgsConstructor;

public interface PatronQueryRepository {
    PatronSnapshot getBy(PatronId id);

    @AllArgsConstructor
    class InMemoryPatronQueryRepository implements PatronQueryRepository {

        private final PatronRepository patronRepository;

        @Override
        public PatronSnapshot getBy(PatronId id) {
            return patronRepository.getBy(id).snapshot();
        }
    }
}
