package it.tarczynski.onion.library.book

import it.tarczynski.onion.library.book.web.CreateBookCommand
import it.tarczynski.onion.library.shared.BaseTestClient
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

class BookCommandClient extends BaseTestClient {

    BookCommandClient(TestRestTemplate restTemplate) {
        super(restTemplate)
    }

    ResponseEntity<Map> execute(CreateBookCommand command) {
        restTemplate.exchange(
                RequestEntity.post('/books/commands/create')
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(command),
                Map.class
        )
    }
}
