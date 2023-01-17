package it.tarczynski.onion.library.reader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateReaderCommand {

    private Integer age;

    ReaderAge age() {
        return ReaderAge.of(age);
    }
}
