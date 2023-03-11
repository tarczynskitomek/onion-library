package it.tarczynski.onion.library.patron;

public record PatronName(String value) {

    static PatronName of(String value) {
        return new PatronName(value);
    }
}
