package it.tarczynski.onion.library.reader;

public record ReaderAge(int value) {

    public static ReaderAge of(int age) {
        return new ReaderAge(age);
    }
}
