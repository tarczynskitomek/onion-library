package it.tarczynski.onion.library.patron

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

    @Override
    ResponseAssertions self() {
        this
    }

    @Override
    ResponseEntity<Map> response() {
        response
    }
}
