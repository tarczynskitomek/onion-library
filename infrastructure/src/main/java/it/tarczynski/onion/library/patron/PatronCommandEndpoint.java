package it.tarczynski.onion.library.patron;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/library/patrons")
@AllArgsConstructor
class PatronCommandEndpoint {

    private final Patrons patrons;

    @PostMapping
    ResponseEntity<PatronResponse> createPatron(
            @RequestBody CreatePatronRequest request
    ) {
        PatronResponse patron = patrons.createPatron(request);
        return ResponseEntity
                .created(URI.create("/patrons/" + patron.id()))
                .body(patron);
    }
}

