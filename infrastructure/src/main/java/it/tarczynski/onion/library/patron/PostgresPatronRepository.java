package it.tarczynski.onion.library.patron;

import it.tarczynski.onion.library.generated.tables.records.PatronsRecord;
import it.tarczynski.onion.library.shared.exception.ResourceNotFound;
import it.tarczynski.onion.library.shared.repository.BasePostgresRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import static it.tarczynski.onion.library.generated.tables.Patrons.PATRONS;

@Component
@AllArgsConstructor
// jOOQ operations implement AutoCloseable interface, but the resources are closed eagerly - no need to do that by the client code
@SuppressWarnings("resource")
class PostgresPatronRepository extends BasePostgresRepository implements PatronRepository {

    private final DSLContext dsl;

    @Override
    public Patron create(Patron patron) {
        final PatronSnapshot snapshot = patron.snapshot();
        dsl.insertInto(PATRONS)
                .set(PATRONS.ID, snapshot.id().value().toString())
                .set(PATRONS.NAME, snapshot.name().value())
                .set(PATRONS.TYPE, snapshot.type().name())
                .set(PATRONS.AFFILIATION, snapshot.affiliation().map(PatronAffiliation::value).orElse(null))
                .execute();
        return patron;
    }

    @Override
    public Patron getBy(PatronId id) {
        final PatronsRecord record = dsl.fetchOne(PATRONS, PATRONS.ID.eq(id.value().toString()));
        if (record == null) {
            throw new ResourceNotFound("Requested patron [%s] does not exist".formatted(id.value()));
        }
        final PatronSnapshot snapshot = PatronSnapshot.builder()
                .id(PatronId.from(record.getId()))
                .name(PatronName.of(record.getName()))
                .type(PatronType.valueOf(record.getType()))
                .affiliation(PatronAffiliation.of(record.getAffiliation()))
                .build();
        return Patron.from(snapshot);
    }
}
