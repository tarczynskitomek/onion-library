package it.tarczynski.onion.library.test.patron


import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class PatronResponseAssert {

    private final ResponseEntity<Map> response

    static PatronResponseAssert assertThat(ResponseEntity<Map> response) {
        new PatronResponseAssert(response)
    }

    private PatronResponseAssert(ResponseEntity<Map> response) {
        this.response = response
    }

    PatronResponseAssert isNotNull() {
        assert response != null
        this
    }

    PatronResponseAssert hasStatus(HttpStatus status) {
        assert response.statusCode == status
        this
    }

    PatronBodyAssert hasPatronThat() {
        assert response.body != null
        new PatronBodyAssert(response.body)
    }

    static class PatronBodyAssert {

            private final Map body

            PatronBodyAssert(Map body) {
                this.body = body
            }

            PatronBodyAssert hasId() {
                assert body.id != null
                this
            }

            PatronBodyAssert hasType(String type) {
                assert body.type == type
                this
            }

            PatronBodyAssert hasName(String name) {
                assert body.name == name
                this
            }

            PatronBodyAssert hasSurname(String surname) {
                assert body.surname == surname
                this
            }
    }
}