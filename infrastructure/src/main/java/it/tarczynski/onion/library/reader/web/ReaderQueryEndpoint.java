package it.tarczynski.onion.library.reader.web;

import it.tarczynski.onion.library.reader.ReaderId;
import it.tarczynski.onion.library.reader.ReaderQueryRepository;
import it.tarczynski.onion.library.reader.ReaderSnapshot;
import it.tarczynski.onion.library.shared.ApiV1;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ApiV1
@AllArgsConstructor
class ReaderQueryEndpoint {

    private final ReaderQueryRepository readerQueryRepository;

    @GetMapping("/readers/{id}")
    ReaderSnapshot getReader(@PathVariable("id") String id) {
        return readerQueryRepository.getBy(ReaderId.from(id));
    }
}
