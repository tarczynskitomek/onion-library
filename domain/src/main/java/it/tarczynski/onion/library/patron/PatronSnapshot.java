package it.tarczynski.onion.library.patron;

import lombok.Builder;

@Builder
public record PatronSnapshot(String id, Integer age) {

    public boolean isAdult() {
        return age >= 18;
    }
}
