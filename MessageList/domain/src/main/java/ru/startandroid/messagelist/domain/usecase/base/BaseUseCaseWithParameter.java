package ru.startandroid.messagelist.domain.usecase.base;

public abstract class BaseUseCaseWithParameter<P, T> {

    protected abstract T createObservable(P parameter);

    public T execute(P parameter) {
        return createObservable(parameter);
    }


}
