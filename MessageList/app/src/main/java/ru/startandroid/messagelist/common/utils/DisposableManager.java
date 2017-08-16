package ru.startandroid.messagelist.common.utils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DisposableManager {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void add(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void remove(Disposable disposable) {
        if (disposable != null) {
            compositeDisposable.remove(disposable);
        }
    }

    public void clear() {
        compositeDisposable.clear();
    }

}
