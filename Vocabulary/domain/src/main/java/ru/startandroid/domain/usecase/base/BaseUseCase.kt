package ru.startandroid.domain.usecase.base

abstract class BaseUseCase<R> {

    abstract fun createObservable(): R

    fun execute() = createObservable()

}