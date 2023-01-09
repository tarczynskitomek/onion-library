package it.tarczynski.onion.library.shared;

public record Title(String value) {
    public static Title of(String title) {
        return new Title(title);
    }
}
