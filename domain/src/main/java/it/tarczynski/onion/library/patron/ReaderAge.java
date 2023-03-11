package it.tarczynski.onion.library.patron;

public record ReaderAge(int value) {

    public static ReaderAge of(int age) {
        return new ReaderAge(age);
    }
}
