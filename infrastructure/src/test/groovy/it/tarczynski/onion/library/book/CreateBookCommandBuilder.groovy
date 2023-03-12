package it.tarczynski.onion.library.book

import groovy.transform.CompileStatic
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

import static it.tarczynski.onion.library.TestUtil.randomUUIDString

@CompileStatic
@Builder(builderStrategy = SimpleStrategy, prefix = "with")
class CreateBookCommandBuilder {

    BookType type = BookType.CIRCULATING
    String title = 'The Lord of the Rings'
    CreateBookCommand.Author author = new CreateBookCommand.Author(randomUUIDString())

    static CreateBookCommandBuilder createBookCommand() {
        new CreateBookCommandBuilder()
    }

    CreateBookCommand build() {
        new CreateBookCommand(type, title, author)
    }
}
