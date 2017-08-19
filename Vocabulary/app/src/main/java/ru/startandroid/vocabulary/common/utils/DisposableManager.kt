package ru.startandroid.vocabulary.common.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposableManager {

    private val compositeDisposable = CompositeDisposable()

    fun add(disposable : Disposable) = compositeDisposable.add(disposable)

    fun remove(disposable : Disposable) = compositeDisposable.remove(disposable)

    fun clear() = compositeDisposable.clear();

}