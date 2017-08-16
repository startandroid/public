package ru.startandroid.messagelist.ui.message.list;

import android.content.Context;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import ru.startandroid.messagelist.R;
import ru.startandroid.messagelist.common.utils.HttpUtils;
import ru.startandroid.messagelist.data.message.UiMessage;

public class MessageListBinding implements MessageListContract.BindingPresenter, MessageListContract.BindingView {

    enum VisibleView {LOADING, TRY_AGAIN, EMPTY, NONE}

    private final BehaviorRelay<List<UiMessage>> messages = BehaviorRelay.createDefault(new ArrayList<>());
    private final PublishRelay<Throwable> errorsThrowable = PublishRelay.create();
    private final PublishRelay<String> errorsString = PublishRelay.create();
    private final BehaviorRelay<Boolean> emptyVisibility = BehaviorRelay.createDefault(false);
    private final BehaviorRelay<Boolean> loadingVisibility = BehaviorRelay.createDefault(false);
    private final BehaviorRelay<Boolean> tryAgainVisibility = BehaviorRelay.createDefault(false);
    private final PublishRelay<VisibleView> visibility = PublishRelay.create();
    private final PublishRelay<Integer> notifyDataChanged = PublishRelay.create();

    public MessageListBinding(Context context) {

        errorsThrowable
                .map(throwable -> HttpUtils.isHttp404(throwable) ? context.getString(R.string.finish) : throwable.getMessage())
                .subscribe(errorsString);

        visibility.map(visibleView -> visibleView == VisibleView.LOADING)
                .distinctUntilChanged()
                .subscribe(loadingVisibility);

        visibility.map(visibleView -> visibleView == VisibleView.TRY_AGAIN)
                .distinctUntilChanged()
                .subscribe(tryAgainVisibility);

        visibility.map(visibleView -> visibleView == VisibleView.EMPTY)
                .distinctUntilChanged()
                .subscribe(emptyVisibility);

        messages.map(messages1 -> -1)
                .subscribe(notifyDataChanged);


    }


    // data presenter

    @Override
    public BehaviorRelay<List<UiMessage>> messages() {
        return messages;
    }

    @Override
    public PublishRelay<Throwable> errorsThrowable() {
        return errorsThrowable;
    }

    @Override
    public PublishRelay<Integer> notifyDataChanged() {
        return notifyDataChanged;
    }

    @Override
    public void showEmpty() {
        visibility.accept(VisibleView.EMPTY);
    }

    @Override
    public void showLoading() {
        visibility.accept(VisibleView.LOADING);
    }

    @Override
    public void showTryAgain() {
        visibility.accept(VisibleView.TRY_AGAIN);
    }

    @Override
    public void showNone() {
        visibility.accept(VisibleView.NONE);
    }


    // data view

    @Override
    public Observable<String> getErrors() {
        return errorsString.hide();
    }

    @Override
    public Observable<Boolean> getEmptyVisibility() {
        return emptyVisibility.hide();
    }

    @Override
    public Observable<Boolean> getLoadingVisibility() {
        return loadingVisibility.hide();
    }

    @Override
    public Observable<Boolean> getTryAgainVisibility() {
        return tryAgainVisibility.hide();
    }

    @Override
    public Observable<Integer> getNotifyDataChanged() {
        return notifyDataChanged().hide();
    }


}
