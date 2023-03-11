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
        hasStatus(HttpStatus.ACCEPTED)
    }

    ResponseAssertions isOK() {
        hasStatus(HttpStatus.OK)
    }

    ResponseAssertions hasStatus(HttpStatus expected) {
        assert response.statusCode == expected
        this
    }

    BodyAssertions hasPatronThat() {
        assert response.body != null
        new BodyAssertions(response.body)
    }

    BodyAssertions hasErrorThat() {
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

        BodyAssertions hasAffiliation(String expected) {
            assert body.affiliation == expected
            this
        }

        BodyAssertions isRegular() {
            assert body.regular
            assert !body.researcher
            this
        }

        BodyAssertions isResearcher() {
            assert body.researcher
            assert !body.regular
            this
        }

        BodyAssertions hasMessage(String expected) {
            assert body.message == expected
            this
        }
    }
}
