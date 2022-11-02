package it.tarczynski.onion.library.book


import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable

import java.time.Instant

class ResponseAssertions {

    private final ResponseEntity<Map> response

    private ResponseAssertions(ResponseEntity<Map> response) {
        this.response = response
    }

    static ResponseAssertions assertThat(ResponseEntity<Map> response) {
        new ResponseAssertions(response)
    }

    ResponseAssertions isAccepted() {
        assert response.statusCode == HttpStatus.ACCEPTED
        this
    }

    Assertions containsBookThat() {
        final BookSnapshot snapshot = fromBody()
        Assertions.assertThat(snapshot)
    }

    ResponseAssertions isOK() {
        assert response.statusCode == HttpStatus.OK
        this
    }

    private BookSnapshot fromBody() {
        new BookSnapshot(
                response.body.id as String,
                response.body.authorId as String,
                response.body.title as String,
                parseNullableInstant(response.body.createdAt as String),
                parseNullableInstant(response.body.approvedAt as String),
                parseNullableInstant(response.body.rejectedAt as String),
                parseNullableInstant(response.body.archivedAt as String),
                response.body.status as BookSnapshot.Status,
        )
    }

    private static Instant parseNullableInstant(@Nullable String text) {
        text != null ? Instant.parse(text) : null
    }
}
