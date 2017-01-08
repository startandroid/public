package ru.startandroid.messagelist.ui.messagelist.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.startandroid.messagelist.R;
import ru.startandroid.messagelist.data.message.Message;
import ru.startandroid.messagelist.ui.messagelist.MessageListFragment;
import ru.startandroid.messagelist.utils.clicks.Click;
import rx.Observable;
import rx.subjects.PublishSubject;


public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_MESSAGE = 1;
    private static final int VIEW_TYPE_TRY_AGAIN = 2;
    private static final int VIEW_TYPE_PROGRESS = 3;

    private CheckedChecker checkedChecker = CheckedChecker.EMPTY;

    private final DataState dataStateNone = new DataStateNone();
    private final DataState dataStateProgress = new DataStateProgress();
    private final DataState dataStateTryAgain = new DataStateTryAgain();

    private DataState dataState = dataStateNone;

    private final PublishSubject<Click<MessageListHolder>> onMessageClickSubject = PublishSubject.create();
    private final PublishSubject<Void> onTryAgainClickSubject = PublishSubject.create();

    private final Context context;
    private final Picasso picasso;

    private final List<Message> data = new ArrayList<>();

    public MessageListAdapter(Context context, Picasso picasso) {
        this.context = context;
        this.picasso = picasso;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_MESSAGE:
                View view = LayoutInflater.from(context).inflate(R.layout.message_list_item, parent, false);
                MessageListHolder messageListHolder = new MessageListHolder(view, picasso);
                messageListHolder.getClicks().subscribe(onMessageClickSubject);
                return messageListHolder;
            case VIEW_TYPE_TRY_AGAIN:
                View viewTryAgain = LayoutInflater.from(context).inflate(R.layout.list_item_try_again, parent, false);
                TryAgainHolder tryAgainHolder = new TryAgainHolder(viewTryAgain);
                tryAgainHolder.getTryAgainClicks().subscribe(onTryAgainClickSubject);
                return tryAgainHolder;
            case VIEW_TYPE_PROGRESS:
                View viewProgress = LayoutInflater.from(context).inflate(R.layout.list_item_progress, parent, false);
                return new ProgressHolder(viewProgress);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_MESSAGE) {
            ((MessageListHolder)holder).bind(getItem(position));
            ((MessageListHolder)holder).setChecked(isChecked(getItem(position).getId()));
        }
    }

    public void setCheckedChecker(CheckedChecker checkedChecker) {
        this.checkedChecker = checkedChecker;
    }

    private boolean isChecked(String id) {
        return checkedChecker.isChecked(id);
    }


    @Override
    public int getItemViewType(int position) {
        return dataState.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return dataState.getCount();
    }

    private Message getItem(int position) {
        return data.get(position);
    }

    public void setData(List<Message> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public Observable<Void> getTryAgainClicks() {
        return onTryAgainClickSubject.asObservable();
    }

    public Observable<Click<MessageListHolder>> getMessageClicks() {
        return onMessageClickSubject.asObservable();
    }

    public boolean canBeSwiped(int position) {
        return !(position == getItemCount() - 1
                && dataState != dataStateNone);
    }

    public void setDataState(MessageListFragment.DataState dataState) {
        switch (dataState) {
            case PROGRESS:
                this.dataState = dataStateProgress;
                break;
            case TRY:
                this.dataState = dataStateTryAgain;
                break;
            case NONE:
                this.dataState = dataStateNone;
                break;
        }
        notifyDataSetChanged();
    }

    interface DataState {
        int getCount();
        int getItemViewType(int position);
    }

    private class DataStateNone implements DataState {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            return VIEW_TYPE_MESSAGE;
        }
    }


    private class DataStateTryAgain implements DataState {

        @Override
        public int getCount() {
            return data.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getCount() - 1) {
                return VIEW_TYPE_TRY_AGAIN;
            } else {
                return VIEW_TYPE_MESSAGE;
            }
        }
    }

    private class DataStateProgress implements DataState {

        @Override
        public int getCount() {
            return data.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getCount() - 1) {
                return VIEW_TYPE_PROGRESS;
            } else {
                return VIEW_TYPE_MESSAGE;
            }
        }
    }


}
