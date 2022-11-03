package it.tarczynski.onion.library.shared.exception;

public class OptimisticLockingException extends IllegalStateException {

    public OptimisticLockingException(String message) {
        super(message);
    }
}
