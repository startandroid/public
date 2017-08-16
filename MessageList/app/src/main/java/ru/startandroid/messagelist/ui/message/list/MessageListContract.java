package ru.startandroid.messagelist.ui.message.list;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;

import java.util.List;

import io.reactivex.Observable;
import ru.startandroid.messagelist.base.mvp.MvpPresenter;
import ru.startandroid.messagelist.base.mvp.MvpView;
import ru.startandroid.messagelist.common.utils.clicks.Click;
import ru.startandroid.messagelist.data.message.UiMessage;

public class MessageListContract {

    public interface View extends MvpView {
    }

    public interface Presenter extends MvpPresenter<View> {
        BindingView getDataView();

        void onScrollToEnd();

        void tryAgain();

        void click(Click<Integer> click);
    }

    public interface BindingPresenter {
        BehaviorRelay<List<UiMessage>> messages();

        PublishRelay<Throwable> errorsThrowable();

        PublishRelay<Integer> notifyDataChanged();

        void showEmpty();

        void showLoading();

        void showTryAgain();

        void showNone();

    }

    public interface BindingView {
        Observable<String> getErrors();

        Observable<Boolean> getEmptyVisibility();

        Observable<Boolean> getLoadingVisibility();

        Observable<Boolean> getTryAgainVisibility();

        Observable<Integer> getNotifyDataChanged();
    }
}

