package ru.startandroid.messagelist.ui.message.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.functions.Consumer;
import ru.startandroid.messagelist.base.adapter.AdapterDataSource;
import ru.startandroid.messagelist.common.utils.clicks.Click;
import ru.startandroid.messagelist.data.message.UiMessage;
import ru.startandroid.messagelist.ui.message.list.adapter.delegate.AdapterDelegate;

public class AdapterDelegatesManager {

    private final Set<AdapterDelegate> adapterDelegates = new HashSet<>();
    private final Callback callback;
    private Mode mode = Mode.NONE;

    private final Consumer<Boolean> loadingConsumer = aBoolean -> {
        if (aBoolean) {
            setMode(Mode.LOADING);
        } else {
            if (mode == Mode.LOADING) setMode(Mode.NONE);
        }
    };

    private final Consumer<Boolean> tryAgainConsumer = aBoolean -> {
        if (aBoolean) {
            setMode(Mode.TRY_AGAIN);
        } else {
            if (mode == Mode.TRY_AGAIN) setMode(Mode.NONE);
        }
    };

    AdapterDelegatesManager(Callback callback) {
        this.callback = callback;
    }

    void addAdapterDelegate(AdapterDelegate adapterDelegate) {
        adapterDelegates.add(adapterDelegate);
    }

    private AdapterDelegate getAdapterDelegateForViewType(int viewType) {
        for (AdapterDelegate adapterDelegate : adapterDelegates) {
            if (adapterDelegate.getViewType() == viewType) {
                return adapterDelegate;
            }
        }
        return null;
    }

    private void setMode(Mode mode) {
        this.mode = mode;
        callback.notifyChanges();
    }

    int getItemViewType(AdapterDataSource<UiMessage> adapterDataSource, int position) {
        if (position == getItemCount(adapterDataSource) - 1) {
            return mode.extraViewType.ordinal();
        }
        return ViewType.MESSAGE.ordinal();
    }

    int getItemCount(AdapterDataSource<UiMessage> adapterDataSource) {
        return adapterDataSource.getItemCount() + mode.extraCount;
    }

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, Consumer<Click<Integer>> clickConsumer) {
        return getAdapterDelegateForViewType(viewType).onCreateViewHolder(parent, clickConsumer);
    }

    void onBindViewHolder(AdapterDataSource<UiMessage> adapterDataSource, RecyclerView.ViewHolder holder, int position) {
        getAdapterDelegateForViewType(getItemViewType(adapterDataSource, position))
                .onBindViewHolder(adapterDataSource, holder, position);
    }

    Consumer<Boolean> getLoadingConsumer() {
        return loadingConsumer;
    }

    Consumer<Boolean> getTryAgainConsumer() {
        return tryAgainConsumer;
    }

    public boolean isAvailableToLoad() {
        return mode == Mode.NONE;
    }

    public enum ViewType {MESSAGE, TRY_AGAIN, LOADING}

    private enum Mode {
        NONE(0, ViewType.MESSAGE), TRY_AGAIN(1, ViewType.TRY_AGAIN), LOADING(1, ViewType.LOADING);

        private final int extraCount;
        private final ViewType extraViewType;

        Mode(int extraCount, ViewType extraViewType) {
            this.extraCount = extraCount;
            this.extraViewType = extraViewType;
        }
    }

    interface Callback {
        void notifyChanges();
    }
}
