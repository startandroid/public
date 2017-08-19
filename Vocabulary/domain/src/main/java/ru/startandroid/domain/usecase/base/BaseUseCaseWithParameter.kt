package ru.startandroid.domain.usecase.base

abstract class BaseUseCaseWithParameter<P, R> {

    abstract fun createObservable(parameter: P): R

    fun execute(parameter: P) = createObservable(parameter)

}