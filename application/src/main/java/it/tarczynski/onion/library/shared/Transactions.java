package it.tarczynski.onion.library.shared;

import java.util.function.Supplier;

@FunctionalInterface
public interface Transactions {

    <T> T execute(Supplier<T> operation);
}
