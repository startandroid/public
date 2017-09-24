package ru.startandroid.domain.usecase.base

import io.reactivex.Completable

abstract class CompletableUseCaseWithParameter<P> : BaseUseCaseWithParameter<P, Completable>()