package ru.startandroid.vocabulary.base.mvp;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

    void destroy();

}
