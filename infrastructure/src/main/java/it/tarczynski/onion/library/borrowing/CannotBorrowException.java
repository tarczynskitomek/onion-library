package it.tarczynski.onion.library.borrowing;

public class CannotBorrowException extends RuntimeException {

    public CannotBorrowException(String message) {
        super(message);
    }
}
