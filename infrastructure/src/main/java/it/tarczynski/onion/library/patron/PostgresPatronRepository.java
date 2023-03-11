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
                .set(PATRONS.ID, snapshot.id())
                .set(PATRONS.AGE, snapshot.age())
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
                .id(record.getId())
                .age(record.getAge())
                .build();
        return Patron.from(snapshot);
    }
}
