package it.tarczynski.onion.library.patron

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

    ResponseAssertions isAccepted() {
        assert response.statusCode == HttpStatus.ACCEPTED
        this
    }

    ResponseAssertions isOK() {
        assert response.statusCode == HttpStatus.OK
        this
    }

    BodyAssertions hasPatronThat() {
        assert response.body != null
        new BodyAssertions(response.body)
    }

    static class BodyAssertions {

        private final Map body

        BodyAssertions(Map body) {
            this.body = body
        }

        BodyAssertions hasName(String expected) {
            assert body.name == expected
            this
        }

        BodyAssertions isRegular() {
            assert body.regular
            this
        }
    }
}
