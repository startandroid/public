package ru.startandroid.messagelist.ui.messagelist;

import android.support.v7.view.ActionMode;
import android.view.Menu;

import java.util.List;

import ru.startandroid.messagelist.data.message.Message;
import ru.startandroid.messagelist.ui.base.MvpPresenter;
import ru.startandroid.messagelist.ui.base.MvpView;
import ru.startandroid.messagelist.ui.messagelist.list.CheckedChecker;
import ru.startandroid.messagelist.ui.messagelist.list.MessageListHolder;
import ru.startandroid.messagelist.utils.clicks.Click;

public interface MessageListContract {

    interface View extends MvpView {
        void setMessages(List<Message> messages);
        void setDataState(MessageListFragment.DataState dataState);
        void setActionModeEnabled(boolean enabled);
        void invalidateActionMode();
        void setCheckedIds(CheckedChecker checkedChecker);
        void openDetails(MessageListHolder messageListHolder);
    }

    interface Presenter extends MvpPresenter<View> {
        void getMessages();
        void messageSwiped(int position);
        void onTryAgainClick();
        void onScrollToEnd();
        void onMessageClick(Click<MessageListHolder> messageClick);
        boolean onPrepareActionMode(ActionMode mode, Menu menu);
        void onActionMenuDelete();
        void onDestroyActionMode();
    }

}
