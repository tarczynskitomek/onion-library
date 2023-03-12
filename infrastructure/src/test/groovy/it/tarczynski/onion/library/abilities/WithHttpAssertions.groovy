package it.tarczynski.onion.library.abilities

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

trait WithHttpAssertions<ASSERTIONS> extends WithAssertions<ASSERTIONS> {

    abstract ResponseEntity<Map> response()

    ASSERTIONS hasStatus(HttpStatus expected) {
        assert response().statusCode == expected
        self()
    }

    ASSERTIONS isOK() {
        assert response().statusCode == HttpStatus.OK
        self()
    }

    ASSERTIONS isAccepted() {
        hasStatus(HttpStatus.ACCEPTED)
        self()
    }
}
