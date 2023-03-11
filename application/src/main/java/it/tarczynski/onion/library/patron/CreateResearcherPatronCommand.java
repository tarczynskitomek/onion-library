package it.tarczynski.onion.library.patron;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
class CreateResearcherPatronCommand {

    @NotBlank(message = "Researcher name cannot be null or blank")
    private String name;
    @NotBlank(message = "Researcher affiliation cannot be null or blank")
    private String affiliation;

    PatronName name() {
        return PatronName.of(name);
    }

    PatronAffiliation affiliation() {
        return PatronAffiliation.of(affiliation);
    }

    @Override
    public String toString() {
        return "CreateResearcherPatronCommand[" +
                "name=" + name + ", " +
                "affiliation=" + affiliation + ']';
    }

}
