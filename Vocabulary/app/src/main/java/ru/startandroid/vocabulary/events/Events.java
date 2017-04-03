package ru.startandroid.vocabulary.events;

public class Events {

    public static Event EMPTY = new Event();

    public static Event recordsUpdated() {
        return createEvent(EventType.RECORDS_UPDATED);
    }

    public static Event exercisesUpdated() {
        return createEvent(EventType.EXERCISES_UPDATED);
    }



    private static Event createEvent(int type) {
        Event event = new Event();
        event.setType(type);
        return event;
    }

}
