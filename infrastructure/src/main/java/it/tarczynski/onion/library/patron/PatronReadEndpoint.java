package it.tarczynski.onion.library.patron;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library/patrons")
@AllArgsConstructor
class PatronReadEndpoint {

    @GetMapping("/{id}")
    PatronResponse getPatron(@PathVariable String id) {
        return new PatronResponse(id, "Joe", "Doe", "REGULAR");
    }
}
