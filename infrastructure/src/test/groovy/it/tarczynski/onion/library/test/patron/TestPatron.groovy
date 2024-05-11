package it.tarczynski.onion.library.test.patron

import groovy.transform.CompileStatic
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@CompileStatic
@Builder(builderStrategy = SimpleStrategy, prefix = 'with')
class TestPatron {

    String type = 'REGULAR'
    String name = 'Joe'
    String surname = 'Doe'

    static TestPatron aPatron() {
        new TestPatron()
    }
}