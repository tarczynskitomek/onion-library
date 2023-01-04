package it.tarczynski.onion.library.book

import groovy.transform.CompileStatic
import it.tarczynski.onion.library.book.web.ApproveBookCommand
import it.tarczynski.onion.library.book.web.ArchiveBookCommand
import it.tarczynski.onion.library.book.web.CreateBookCommand
import it.tarczynski.onion.library.book.web.RejectBookCommand
import it.tarczynski.onion.library.shared.BaseTestClient
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

@CompileStatic
class BookCommandClient extends BaseTestClient {

    BookCommandClient(TestRestTemplate restTemplate) {
        super(restTemplate)
    }

    ResponseEntity<Map> execute(CreateBookCommand command) {
        UUID commandId = generateId()
        restTemplate.exchange(
                RequestEntity.put("/books/commands/create/$commandId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(command),
                Map
        )
    }

    ResponseEntity<Map> execute(ApproveBookCommand command) {
        UUID commandId = generateId()
        restTemplate.exchange(
                RequestEntity.put("/books/commands/approve/$commandId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(command),
                Map
        )
    }

    ResponseEntity<Map> execute(RejectBookCommand command) {
        UUID commandId = generateId()
        restTemplate.exchange(
                RequestEntity.put("/books/commands/reject/$commandId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(command),
                Map
        )
    }

    ResponseEntity<Map> execute(ArchiveBookCommand command) {
        UUID commandId = generateId()
        restTemplate.exchange(
                RequestEntity.put("/books/commands/archive/$commandId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(command),
                Map
        )
    }
}
