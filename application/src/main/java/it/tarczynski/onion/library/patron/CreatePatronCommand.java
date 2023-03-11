package it.tarczynski.onion.library.patron;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatronCommand {

    private Integer age;

    ReaderAge age() {
        return ReaderAge.of(age);
    }
}
