package ru.startandroid.messagelist.common.utils.clicks;

public class Click<I> {

    private final Type type;
    private final I item;

    public Click(Type type, I item) {
        this.type = type;
        this.item = item;
    }

    public Type getType() {
        return type;
    }

    public I getItem() {
        return item;
    }

    public enum Type {
        ITEM, TRY_AGAIN
    }

    @Override
    public String toString() {
        return "Click{" +
                "type=" + type +
                ", item=" + item +
                '}';
    }
}