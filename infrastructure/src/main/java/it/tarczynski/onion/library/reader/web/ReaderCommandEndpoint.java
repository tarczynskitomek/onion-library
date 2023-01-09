package it.tarczynski.onion.library.reader.web;

import it.tarczynski.onion.library.reader.CreateReaderCommand;
import it.tarczynski.onion.library.reader.ReaderSnapshot;
import it.tarczynski.onion.library.reader.Readers;
import it.tarczynski.onion.library.shared.ApiV1;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiV1
@AllArgsConstructor
class ReaderCommandEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(ReaderCommandEndpoint.class);
    private final Readers readers;

    @PutMapping("/readers/commands/create/{id}")
    ResponseEntity<ReaderSnapshot> createReader(
            @RequestBody CreateReaderCommand command,
            @PathVariable("id") String commandId
    ) {
        LOG.info("[{}] Consumed command [{}]", commandId, command);
        final ReaderSnapshot reader = readers.handle(command);
        return ResponseEntity.accepted().body(reader);
    }
}
