package ru.startandroid.vocabulary.base.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class PresenterBase<T extends MvpView> implements MvpPresenter<T> {

    private T view;

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

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
        compositeSubscription.clear();
    }

    public T getView() {
        return view;
    }

    protected boolean isViewAttached() {
        return view != null;
    }

    protected void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    protected void removeSubscription(Subscription subscription) {
        if (subscription != null) {
            compositeSubscription.remove(subscription);
        }
    }

}
