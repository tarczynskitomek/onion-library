package it.tarczynski.onion.library.patron.web;

import it.tarczynski.onion.library.patron.CreateRegularPatronCommand;
import it.tarczynski.onion.library.patron.PatronSnapshot;
import it.tarczynski.onion.library.patron.Patrons;
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
class PatronCommandEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(PatronCommandEndpoint.class);
    private final Patrons patrons;

    @PutMapping("/patrons/commands/create-regular/{id}")
    ResponseEntity<PatronResponse> createRegular(
            @RequestBody CreateRegularPatronCommand command,
            @PathVariable("id") String commandId
    ) {
        LOG.info("[{}] Consumed command [{}]", commandId, command);
        final PatronSnapshot patron = patrons.handle(command);
        return ResponseEntity.accepted().body(
                PatronResponse.from(patron)
        );
    }
}
