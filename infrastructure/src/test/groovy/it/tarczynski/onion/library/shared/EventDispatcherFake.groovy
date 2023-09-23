package it.tarczynski.onion.library.shared

class EventDispatcherFake implements EventDispatcher {

    private final Map<EventId, Event> events = new HashMap<>()

    @Override
    void dispatch(Event event) {
        events.put(event.id(), event)
    }
}
