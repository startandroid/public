package ru.startandroid.vocabulary.base.dagger

interface BaseComponent<O> {
    fun inject(obj: O)
}