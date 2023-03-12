package it.tarczynski.onion.library.patron;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRegularPatronCommand {

    @NotBlank(message = "Patron name cannot be null or blank")
    private String name;

    public PatronName name() {
        return PatronName.of(name);
    }

    @Override
    public String toString() {
        return "CreateRegularPatronCommand[" +
                "name=" + name + ']';
    }

}
