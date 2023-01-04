package it.tarczynski.onion.library.reader;

import lombok.Builder;

@Builder
public record ReaderSnapshot(String id, Integer age) {

    public boolean isAdult() {
        return age >= 18;
    }
}
