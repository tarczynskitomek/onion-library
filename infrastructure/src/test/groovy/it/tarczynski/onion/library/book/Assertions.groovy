package it.tarczynski.onion.library.book

import it.tarczynski.onion.library.shared.ApprovedAt
import it.tarczynski.onion.library.shared.Title

import java.time.Instant

class Assertions {

    private final BookSnapshot subject

    private Assertions(BookSnapshot subject) {
        this.subject = subject
    }

    static Assertions assertThat(BookSnapshot book) {
        new Assertions(book)
    }

    Assertions isAwaitingApproval() {
        assert subject.status() == BookSnapshot.Status.AWAITING_APPROVAL
        this
    }

    Assertions isApproved() {
        assert subject.status() == BookSnapshot.Status.APPROVED
        this
    }

    Assertions hasApprovalDate(Instant expected) {
        assert subject.approvedAtTimeNullable() == expected
        this
    }

    Assertions isRejected() {
        assert subject.status() == BookSnapshot.Status.REJECTED
        this
    }

    Assertions hasRejectionTime(Instant expected) {
        assert subject.rejectedAt().time() == expected
        this
    }

    Assertions hasCreationTime(Instant expected) {
        assert subject.createdAt().time() == expected
        this
    }

    Assertions isArchived() {
        assert subject.status() == BookSnapshot.Status.ARCHIVED
        this
    }

    Assertions hasArchivisationDate(Instant expected) {
        assert subject.archivedAt().time() == expected
        this
    }

    Assertions hasTitle(String expected) {
        assert subject.title() == Title.of(expected)
        this
    }

    Assertions hasAuthor(String expected) {
        assert subject.author().value().toString() == expected
        this
    }

}
