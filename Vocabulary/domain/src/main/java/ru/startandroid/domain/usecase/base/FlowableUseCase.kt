package ru.startandroid.domain.usecase.base

import io.reactivex.Flowable

abstract class FlowableUseCase<R> : BaseUseCase<Flowable<R>>()