package ru.startandroid.messagelist.base.dagger;

public interface BaseComponent<I> {
    void inject(I objectForInject);
}
