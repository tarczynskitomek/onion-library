package it.tarczynski.onion.library.patron;

public record PatronAffiliation(String value){
    public static PatronAffiliation of(String affiliation) {
        return new PatronAffiliation(affiliation);
    }
}
