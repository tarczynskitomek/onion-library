package it.tarczynski.onion.library.reader;

import it.tarczynski.onion.library.generated.tables.records.ReadersRecord;
import it.tarczynski.onion.library.shared.exception.ResourceNotFound;
import it.tarczynski.onion.library.shared.repository.BasePostgresRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import static it.tarczynski.onion.library.generated.tables.Readers.READERS;

@Component
@AllArgsConstructor
// jOOQ operations implement AutoCloseable interface, but the resources are closed eagerly - no need to do that by the client code
@SuppressWarnings("resource")
class PostgresReaderRepository extends BasePostgresRepository implements ReaderRepository {

    private final DSLContext dsl;

    @Override
    public Reader create(Reader reader) {
        final ReaderSnapshot snapshot = reader.snapshot();
        dsl.insertInto(READERS)
                .set(READERS.ID, snapshot.id())
                .set(READERS.AGE, snapshot.age())
                .execute();
        return reader;
    }

    @Override
    public Reader getBy(ReaderId id) {
        final ReadersRecord record = dsl.fetchOne(READERS, READERS.ID.eq(id.value().toString()));
        if (record == null) {
            throw new ResourceNotFound("Requested reader [%s] does not exist".formatted(id.value()));
        }
        final ReaderSnapshot snapshot = ReaderSnapshot.builder()
                .id(record.getId())
                .age(record.getAge())
                .build();
        return Reader.from(snapshot);
    }
}
