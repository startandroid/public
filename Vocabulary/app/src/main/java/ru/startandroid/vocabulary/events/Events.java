package ru.startandroid.vocabulary.events;

public class Events {

    public static Event recordsUpdated() {
        return createEvent(EventType.RECORDS_UPDATED);
    }

    private static Event createEvent(int type) {
        Event event = new Event();
        event.setType(type);
        return event;
    }

}
