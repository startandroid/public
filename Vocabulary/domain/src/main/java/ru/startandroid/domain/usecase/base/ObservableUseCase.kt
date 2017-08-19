package ru.startandroid.domain.usecase.base

import io.reactivex.Observable

abstract class ObservableUseCase<R> : BaseUseCase<Observable<R>>()