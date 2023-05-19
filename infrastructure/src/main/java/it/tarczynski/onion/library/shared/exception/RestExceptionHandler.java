package it.tarczynski.onion.library.shared.exception;

import it.tarczynski.onion.library.borrowing.CannotBorrowException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(CannotBorrowException.class)
    ResponseEntity<ErrorResponse> handle(CannotBorrowException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @Getter
    static class ErrorResponse {

        private final Error error;

        record Error(String message) {
        }

        public ErrorResponse(String message) {
            this.error = new Error(message);
        }
    }
}
