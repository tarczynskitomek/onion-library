package it.tarczynski.onion.library

import it.tarczynski.onion.library.abilities.WithHttpAssertions
import org.springframework.http.ResponseEntity

class ResponseAssertions implements WithHttpAssertions<ResponseAssertions> {

    private final ResponseEntity<Map> response

    ResponseAssertions(ResponseEntity<Map> response) {
        this.response = response
    }

    static ResponseAssertions assertThat(ResponseEntity<Map> response) {
        new ResponseAssertions(response)
    }

    @Override
    ResponseAssertions self() {
        this
    }

    BodyAssertions hasHoldThat() {
        assert response.body != null
        new BodyAssertions(response.body)
    }

    BodyAssertions hasErrorThat() {
        assert response.body?.error != null
        new BodyAssertions(response.body)
    }

    static class BodyAssertions {

        private final Map body

        BodyAssertions(Map body) {
            this.body = body
        }

        BodyAssertions hasId() {
            assert body.id != null
            this
        }

        BodyAssertions hasPatronId(String expected) {
            assert body.patronId == expected
            this
        }

        BodyAssertions hasBookId(String expected) {
            assert body.bookId == expected
            this
        }

        BodyAssertions hasMessage(String expected) {
            assert body.error.message == expected
            this
        }
    }

    @Override
    ResponseEntity<Map> response() {
        response
    }
}
