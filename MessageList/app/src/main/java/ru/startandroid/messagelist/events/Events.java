package ru.startandroid.messagelist.events;

public class Events {

    public static Event messagesUpdated() {
        return createEvent(EventType.MESSAGES_UPDATED);
    }

    private static Event createEvent(int type) {
        Event event = new Event();
        event.setType(type);
        return event;
    }

}
