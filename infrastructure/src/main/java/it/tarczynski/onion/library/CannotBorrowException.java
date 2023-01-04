package it.tarczynski.onion.library;

public class CannotBorrowException extends RuntimeException {

    public CannotBorrowException(String message) {
        super(message);
    }
}
