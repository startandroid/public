package ru.startandroid.messagelist.domain.usecase.base;

import io.reactivex.Observable;

public abstract class ObservableUseCaseWithParams<P, R> extends BaseUseCaseWithParameter<P, Observable<R>> {

}
