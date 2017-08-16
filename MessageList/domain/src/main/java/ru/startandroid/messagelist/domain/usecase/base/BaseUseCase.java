package ru.startandroid.messagelist.domain.usecase.base;

public abstract class BaseUseCase<T> {

    protected abstract T createObservable();

    public T execute() {
        return createObservable();
    }


}
