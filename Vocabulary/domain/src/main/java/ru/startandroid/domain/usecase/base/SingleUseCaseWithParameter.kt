package ru.startandroid.domain.usecase.base

import io.reactivex.Single
import ru.startandroid.domain.model.Word

abstract class SingleUseCaseWithParameter<P, R> : BaseUseCaseWithParameter<P, Single<R>>() {

}