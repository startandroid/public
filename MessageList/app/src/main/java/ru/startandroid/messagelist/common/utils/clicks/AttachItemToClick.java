package ru.startandroid.messagelist.common.utils.clicks;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class AttachItemToClick<I> implements Function<I, Click<I>> {

    private final Click.Type clickType;

    public AttachItemToClick(Click.Type clickType) {
        this.clickType = clickType;
    }

    @Override
    public Click<I> apply(@NonNull I item) throws Exception {
        return new Click<I>(clickType, item);
    }

}
