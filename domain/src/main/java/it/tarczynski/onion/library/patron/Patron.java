package it.tarczynski.onion.library.patron;

import com.google.common.base.Preconditions;
import it.tarczynski.onion.library.patron.PatronSnapshot.PatronSnapshotBuilder;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.function.Supplier;

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

    public static Patron createResearcher(PatronName name, PatronAffiliation affiliation) {
        return new ResearcherPatron(PatronId.next(), name, affiliation);
    }

    static Patron from(PatronSnapshot snapshot) {
        return switch (snapshot.type()) {
            case REGULAR -> new RegularPatron(snapshot.id(), snapshot.name());
            case RESEARCHER -> new ResearcherPatron(
                    snapshot.id(),
                    snapshot.name(),
                    snapshot.affiliation()
                            .orElseThrow(raiseMissingAffiliationException(snapshot.id())));
        };
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

    final static class ResearcherPatron extends Patron {

        private final PatronAffiliation affiliation;

        private ResearcherPatron(PatronId id, PatronName name, PatronAffiliation affiliation) {
            super(id, name);
            Preconditions.checkArgument(Objects.nonNull(affiliation), "Researcher affiliation cannot be null");
            this.affiliation = affiliation;
        }

        @Override
        protected PatronType type() {
            return PatronType.RESEARCHER;
        }

        @Override
        protected PatronSnapshotBuilder visit(PatronSnapshotBuilder visitor) {
            return visitor.affiliation(affiliation);
        }
    }

    protected abstract PatronType type();

    protected PatronSnapshotBuilder visit(PatronSnapshotBuilder visitor) {
        return visitor;
    }

    PatronSnapshot snapshot() {
        return visit(
                PatronSnapshot.builder()
                        .id(id)
                        .name(name)
                        .type(type())
        ).build();
    }

    private static Supplier<MissingAffiliationException> raiseMissingAffiliationException(PatronId id) {
        return () -> new MissingAffiliationException(id);
    }
}
