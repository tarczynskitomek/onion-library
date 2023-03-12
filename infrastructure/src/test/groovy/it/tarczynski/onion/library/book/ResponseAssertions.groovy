package it.tarczynski.onion.library.book

import it.tarczynski.onion.library.abilities.WithHttpAssertions
import it.tarczynski.onion.library.book.web.command.BookResponse
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable

import java.time.Instant

class ResponseAssertions implements WithHttpAssertions<ResponseAssertions> {

    private final ResponseEntity<Map> response

    private ResponseAssertions(ResponseEntity<Map> response) {
        this.response = response
    }

    static ResponseAssertions assertThat(ResponseEntity<Map> response) {
        new ResponseAssertions(response)
    }

    BodyAssertions containsBookThat() {
        final BookResponse snapshot = fromBody()
        BodyAssertions.assertThat(snapshot)
    }

    private BookResponse fromBody() {
        new BookResponse(
                response.body.id as String,
                new BookResponse.Author(response.body.author.id as String),
                response.body.title as String,
                parseNullableInstant(response.body.createdAt as String),
                response.body.status as String,
        )
    }

    private static Instant parseNullableInstant(@Nullable String text) {
        text != null ? Instant.parse(text) : null
    }

    static class BodyAssertions {

        private final BookResponse subject

        private BodyAssertions(BookResponse subject) {
            this.subject = subject
        }

        static BodyAssertions assertThat(BookResponse book) {
            new BodyAssertions(book)
        }

        BodyAssertions isCirculating() {
            assert subject.status() == 'CIRCULATING'
            this
        }

        BodyAssertions hasCreationTime(Instant expected) {
            assert subject.createdAt() == expected
            this
        }

        BodyAssertions hasTitle(String expected) {
            assert subject.title() == expected
            this
        }

        BodyAssertions hasAuthor(String expected) {
            assert subject.author().id() == expected
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
