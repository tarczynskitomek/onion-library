package it.tarczynski.onion.library

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ResponseAssertions {

    private final ResponseEntity<Map> response

    ResponseAssertions(ResponseEntity<Map> response) {
        this.response = response
    }

    static ResponseAssertions assertThat(ResponseEntity<Map> response) {
        new ResponseAssertions(response)
    }

    ResponseAssertions hasStatus(HttpStatus expected) {
        assert response.statusCode == expected
        this
    }

    ResponseAssertions isAccepted() {
        hasStatus(HttpStatus.ACCEPTED)
        this
    }

    BodyAssertions hasLoanThat() {
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

        BodyAssertions hasReaderId(String expected) {
            assert body.readerId == expected
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
}
