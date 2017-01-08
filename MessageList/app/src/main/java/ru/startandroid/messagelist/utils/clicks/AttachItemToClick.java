package ru.startandroid.messagelist.utils.clicks;

import rx.functions.Func1;

public class AttachItemToClick<I> implements Func1<Void, Click<I>> {

    private final Click.Type clickType;
    private I item;

    public AttachItemToClick(Click.Type clickType) {
        this.clickType = clickType;
    }

    @Override
    public Click<I> call(Void aVoid) {
        return new Click<I>(clickType, item);
    }

    public void setItem(I item) {
        this.item = item;
    }
}