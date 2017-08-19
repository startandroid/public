package ru.startandroid.vocabulary.base.dagger

interface ComponentBuilder<C : BaseComponent<*>, M : BaseModule> {
    fun build() : C
    fun module(module : M) : ComponentBuilder<C,M>
}