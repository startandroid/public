package ru.startandroid.messagelist.base.mvp;

import io.reactivex.disposables.Disposable;
import ru.startandroid.messagelist.common.utils.DisposableManager;

public abstract class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    private T view;
    private boolean firstCall = true;

    private final DisposableManager disposableManager = new DisposableManager();

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void destroy() {
        disposableManager.clear();
    }

    public T getView() {
        return view;
    }

    protected boolean isViewAttached() {
        return view != null;
    }

    protected void addDisposable(Disposable disposable) {
        disposableManager.add(disposable);
    }

    protected void removeDisposable(Disposable disposable) {
            disposableManager.remove(disposable);
    }

    @Override
    public void viewIsReady() {
        onViewIsReady(firstCall);
        firstCall = false;
    }

    protected abstract void onViewIsReady(boolean firstCall);


}
