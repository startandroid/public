package ru.startandroid.messagelist.ui.message.list.adapter.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.functions.Consumer;
import ru.startandroid.messagelist.R;
import ru.startandroid.messagelist.base.adapter.AdapterDataSource;
import ru.startandroid.messagelist.common.utils.clicks.Click;
import ru.startandroid.messagelist.data.message.UiMessage;
import ru.startandroid.messagelist.ui.message.list.adapter.AdapterDelegatesManager;

public class AdapterDelegateLoading implements AdapterDelegate<UiMessage, AdapterDelegateLoading.LoadingHolder> {

    @Override
    public int getViewType() {
        return AdapterDelegatesManager.ViewType.LOADING.ordinal();
    }

    @Override
    public LoadingHolder onCreateViewHolder(ViewGroup parent, Consumer<Click<Integer>> clickConsumer) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_loading, parent, false);
        return new LoadingHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterDataSource<UiMessage> adapterDataSource, LoadingHolder holder, int position) {
    }

    static class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(View itemView) {
            super(itemView);
        }

    }
}
