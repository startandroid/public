package ru.startandroid.vocabulary.base.mvp

interface MvpPresenter<in V : MvpView> {

    fun attachView(mvpView: V)

    fun viewIsReady()

    fun detachView()

    fun destroy()

}