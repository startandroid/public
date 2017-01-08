package ru.startandroid.messagelist.ui.base;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();

    void destroy();

}
