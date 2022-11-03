package it.tarczynski.onion.library.shared.exception;

public class ResourceNotFound extends IllegalStateException {

    public ResourceNotFound(String message) {
        super(message);
    }
}
