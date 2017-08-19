package ru.startandroid.vocabulary.base.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T : MvpView> : MvpPresenter<T> {

    var view: T? = null
        private set

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun attachView(mvpView: T) {
        view = mvpView
    }

    override fun detachView() {
        view = null
    }

    override fun destroy() {
        compositeDisposable.clear()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun removeDisposable(disposable: Disposable) {
        compositeDisposable.remove(disposable)
    }

}