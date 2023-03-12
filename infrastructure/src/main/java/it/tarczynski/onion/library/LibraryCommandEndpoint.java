package it.tarczynski.onion.library;

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
class LibraryCommandEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(LibraryCommandEndpoint.class);
    private final Library library;

    @PutMapping("/library/holds/commands/create/{id}")
    ResponseEntity<BookHoldSnapshot> hold(
            @RequestBody HoldBookCommand command,
            @PathVariable("id") String commandId
    ) {
        LOG.info("[{}] Consumed command [{}]", commandId, command);
        final BookHoldSnapshot snapshot = library.handle(command);
        return ResponseEntity.accepted().body(snapshot);
    }
}
