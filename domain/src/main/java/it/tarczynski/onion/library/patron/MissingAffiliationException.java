package it.tarczynski.onion.library.patron;

class MissingAffiliationException extends IllegalStateException {

    public MissingAffiliationException(PatronId id) {
        super("Researcher Patron [%s] is missing affiliation".formatted(id.value()));
    }
}
