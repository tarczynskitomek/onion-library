package it.tarczynski.onion.library.shared;

public record Version(int value) {

    public static Version from(Integer value) {
        return new Version(value);
    }

    public static Version first() {
        return new Version(1);
    }
}
