package ru.startandroid.messagelist.base.dagger;

public interface BaseComponentBuilder<C extends BaseComponent, M extends BaseModule> {
    C build();
    BaseComponentBuilder<C,M> module(M module);
}
