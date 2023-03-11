package it.tarczynski.onion.library.patron.web;

import it.tarczynski.onion.library.patron.PatronId;
import it.tarczynski.onion.library.patron.PatronQueryRepository;
import it.tarczynski.onion.library.shared.ApiV1;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ApiV1
@AllArgsConstructor
class PatronQueryEndpoint {

    private final PatronQueryRepository patronQueryRepository;

    @GetMapping("/patrons/{id}")
    PatronResponse getPatron(@PathVariable("id") String id) {
        return PatronResponse.from(patronQueryRepository.getBy(PatronId.from(id)));
    }
}
