package it.tarczynski.onion.library.patron;

public record CreateRegularPatronCommand(String name) {

    public PatronName patronName() {
        return PatronName.of(name);
    }
}
