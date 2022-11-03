package it.tarczynski.onion.library.book

import it.tarczynski.onion.library.book.web.command.BookResponse
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

    BodyAssertions containsBookThat() {
        final BookResponse snapshot = fromBody()
        BodyAssertions.assertThat(snapshot)
    }

    ResponseAssertions isOK() {
        assert response.statusCode == HttpStatus.OK
        this
    }

    private BookResponse fromBody() {
        new BookResponse(
                response.body.id as String,
                new BookResponse.Author(response.body.author.id as String),
                response.body.title as String,
                parseNullableInstant(response.body.createdAt as String),
                parseNullableInstant(response.body.approvedAt as String),
                parseNullableInstant(response.body.rejectedAt as String),
                parseNullableInstant(response.body.archivedAt as String),
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

        BodyAssertions isAwaitingApproval() {
            assert subject.status() == 'AWAITING_APPROVAL'
            this
        }

        BodyAssertions isApproved() {
            assert subject.status() == 'APPROVED'
            this
        }

        BodyAssertions hasApprovalDate(Instant expected) {
            assert subject.approvedAt() == expected
            this
        }

        BodyAssertions isRejected() {
            assert subject.status() == 'REJECTED'
            this
        }

        BodyAssertions hasRejectionTime(Instant expected) {
            assert subject.rejectedAt() == expected
            this
        }

        BodyAssertions hasCreationTime(Instant expected) {
            assert subject.createdAt() == expected
            this
        }

        BodyAssertions isArchived() {
            assert subject.status() == 'ARCHIVED'
            this
        }

        BodyAssertions hasArchivisationDate(Instant expected) {
            assert subject.archivedAt() == expected
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
}
