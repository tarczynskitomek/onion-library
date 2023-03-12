package it.tarczynski.onion.library.book


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

    Assertions isCirculating() {
        assert subject.type() == BookType.CIRCULATING
        this
    }

    Assertions isRestricted() {
        assert subject.type() == BookType.RESTRICTED
        this
    }

    Assertions hasCreationTime(Instant expected) {
        assert subject.createdAt().time() == expected
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
