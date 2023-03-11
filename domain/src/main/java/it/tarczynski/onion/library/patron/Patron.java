package it.tarczynski.onion.library.patron;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@EqualsAndHashCode(of = "id")
abstract sealed class Patron {

    private final PatronId id;
    private final PatronName name;

    private Patron(PatronId id, PatronName name) {
        Preconditions.checkArgument(Objects.nonNull(id), "Patron id cannot be null");
        Preconditions.checkArgument(Objects.nonNull(name), "Patron name cannot be null");
        this.id = id;
        this.name = name;
    }

    public static RegularPatron createRegular(PatronName name) {
        return new RegularPatron(PatronId.next(), name);
    }

    final static class RegularPatron extends Patron {

        private RegularPatron(PatronId id, PatronName name) {
            super(id, name);
        }

        @Override
        protected PatronType type() {
            return PatronType.REGULAR;
        }
    }

    public static Patron from(PatronSnapshot snapshot) {
        return new RegularPatron(snapshot.id(), snapshot.name());
    }

    protected abstract PatronType type();

    PatronSnapshot snapshot() {
        return PatronSnapshot.builder()
                .id(id)
                .name(name)
                .type(type())
                .build();
    }
}
