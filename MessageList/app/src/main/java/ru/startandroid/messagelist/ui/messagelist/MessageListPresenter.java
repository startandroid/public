package ru.startandroid.messagelist.ui.messagelist;

import android.support.v7.view.ActionMode;
import android.view.Menu;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import ru.startandroid.messagelist.data.message.Message;
import ru.startandroid.messagelist.data.message.MessageController;
import ru.startandroid.messagelist.data.message.storage.SqlSpecificationRawAllMessages;
import ru.startandroid.messagelist.events.Event;
import ru.startandroid.messagelist.events.EventBus;
import ru.startandroid.messagelist.events.EventType;
import ru.startandroid.messagelist.storage.Preferences;
import ru.startandroid.messagelist.ui.base.PresenterBase;
import ru.startandroid.messagelist.ui.messagelist.list.CheckedListHelper;
import ru.startandroid.messagelist.ui.messagelist.list.MessageListHolder;
import ru.startandroid.messagelist.utils.CollectionUtils;
import ru.startandroid.messagelist.utils.clicks.Click;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

public class MessageListPresenter extends PresenterBase<MessageListContract.View> implements MessageListContract.Presenter {

    private final MessageController messageController;
    private final Preferences preferences;

    private boolean notLoadedYet = true;
    private boolean noMoreData;
    private List<Message> messages = new ArrayList<>();
    private final CheckedListHelper checkedListHelper;

    private Subscription dbSubscription;
    private Subscription webSubscription;

    private MessageListFragment.DataState viewDataState = MessageListFragment.DataState.NONE;
    private boolean actionModeEnabled = false;


    public MessageListPresenter(MessageController messageController, EventBus eventBus, Preferences preferences) {
        this.messageController = messageController;
        this.preferences = preferences;

        noMoreData = preferences.getNoMoreData();

        addSubscription(eventBus.getEventsObservable(EventType.MESSAGES_UPDATED).subscribe(new Action1<Event>() {
            @Override
            public void call(Event event) {
                loadDataFromDb();
            }
        }));

        checkedListHelper = new CheckedListHelper();
        checkedListHelper.getCheckedCountChangeObservable().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer count) {
                setCheckedCount(count);
            }
        });
    }

    @Override
    public void getMessages() {
        if (notLoadedYet) {
            loadDataFromDb();
        } else {
            restoreView();
        }
    }

    private void restoreView() {
        setMessagesToView();
        setDataStateToView();
        setActionModeEnabledToView();
        setCheckedListToView();
    }

    private void loadDataFromDb() {
        if (isDataLoadingFromDb()) {
            return;
        }
        notLoadedYet = false;
        removeSubscription(dbSubscription);
        dbSubscription = messageController.getMessages(new SqlSpecificationRawAllMessages())
                .subscribe(new Action1<List<Message>>() {
                    @Override
                    public void call(List<Message> messages) {
                        MessageListPresenter.this.messages = messages;
                        if (messages.size() == 0) {
                            loadDataFromWeb();
                        } else {
                            setMessagesToView();
                        }
                    }
                });
        addSubscription(dbSubscription);
    }

    private boolean isDataLoadingFromDb() {
        return dbSubscription != null && !dbSubscription.isUnsubscribed();
    }

    private void loadNewDataFromWeb() {
        if (noMoreData) {
            return;
        }
        if (isDataLoadingFromDb()) {
            return;
        }

        loadDataFromWeb();
    }

    @Override
    public void onTryAgainClick() {
        loadNewDataFromWeb();
    }

    @Override
    public void onScrollToEnd() {
        loadNewDataFromWeb();
    }

    @Override
    public void onMessageClick(Click<MessageListHolder> messageClick) {
        switch (messageClick.getType()) {
            case ITEM_CLICK:
                onMessageClickHandle(messageClick.getItem());
                break;
            case ITEM_LONG_CLICK:
                onMessageLongClickHandle(messageClick.getItem());
                break;
        }
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        mode.setTitle(checkedListHelper.count() + " selected");
        return true;
    }

    @Override
    public void onActionMenuDelete() {
        markAsDeleted(checkedListHelper.getCheckedIdList());
        setActionModeEnabled(false);
    }

    @Override
    public void onDestroyActionMode() {
        checkedListHelper.clear();
        actionModeEnabled = false;
    }

    @Override
    public void undoSnackbarClick(Collection<Long> ids) {
        messageController.markDeleted(ids, false).subscribe();
    }

    private void setCheckedCount(int count) {
        if (count == 0) {
            setActionModeEnabled(false);
        } else {
            getView().invalidateActionMode();
        }
        setCheckedListToView();
    }

    private boolean isActionModeEnabled() {
        return actionModeEnabled;
    }

    private void setActionModeEnabled(boolean enabled) {
        actionModeEnabled = enabled;
        setActionModeEnabledToView();
    }

    private void onMessageClickHandle(MessageListHolder messageListHolder) {
        if (isActionModeEnabled()) {
            toggleCheckedItem(messageListHolder.getMessage().getId());
        } else {
            getView().openDetails(messageListHolder);
        }
    }

    private void onMessageLongClickHandle(MessageListHolder messageListHolder) {
        if (!isActionModeEnabled()) {
            setActionModeEnabled(true);
        }
        toggleCheckedItem(messageListHolder.getMessage().getId());
    }

    private void toggleCheckedItem(Long id) {
        checkedListHelper.toggleChecked(id);
    }


    private void setDataState(MessageListFragment.DataState dataState) {
        viewDataState = dataState;
        setDataStateToView();
    }

    private void loadDataFromWeb() {
        if (isDataLoadingFromWeb() || viewDataState == MessageListFragment.DataState.PROGRESS) {
            return;
        }
        setDataState(MessageListFragment.DataState.PROGRESS);

        removeSubscription(webSubscription);
        webSubscription = messageController.loadMessages()
                .subscribe(new Action0() {
                               @Override
                               public void call() {
                                   setDataState(MessageListFragment.DataState.NONE);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                if (isHttp404(throwable)) {
                                    noMoreData = true;
                                    preferences.setNoMoreData(true);
                                    setDataState(MessageListFragment.DataState.NONE);
                                } else {
                                    setDataState(MessageListFragment.DataState.TRY);
                                }
                            }
                        });
        addSubscription(webSubscription);
    }

    private boolean isDataLoadingFromWeb() {
        return webSubscription != null && !webSubscription.isUnsubscribed();
    }

    private boolean isHttp404(Throwable throwable) {
        return throwable instanceof HttpException
                && ((HttpException)throwable).code() == HttpURLConnection.HTTP_NOT_FOUND;
    }

    private void setDataStateToView() {
        if (isViewAttached()) {
            getView().setDataState(viewDataState);
        }
    }

    private void setMessagesToView() {
        if (messages != null && messages.size() > 0 && isViewAttached()) {
            getView().setMessages(messages);
        }
    }

    private void setActionModeEnabledToView() {
        getView().setActionModeEnabled(actionModeEnabled);
    }

    private void setCheckedListToView() {
        getView().setCheckedIds(checkedListHelper.getCheckedChecker());
    }

    @Override
    public void messageSwiped(int position) {
        markAsDeleted(CollectionUtils.getSet(messages.get(position).getId()));
    }

    private void markAsDeleted(Collection<Long> ids) {
        messageController.markDeleted(ids, true).subscribe();
        getView().showUndoSnackbar(ids);

    }

    @Override
    public void destroy() {
        super.destroy();
        messageController.deleteMarkedMessages().subscribe();
    }
}
