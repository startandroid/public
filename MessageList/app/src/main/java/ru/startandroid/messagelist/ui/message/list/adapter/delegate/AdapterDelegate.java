package ru.startandroid.messagelist.ui.message.list.adapter.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import io.reactivex.functions.Consumer;
import ru.startandroid.messagelist.base.adapter.AdapterDataSource;
import ru.startandroid.messagelist.common.utils.clicks.Click;

public interface AdapterDelegate<T, H extends RecyclerView.ViewHolder> {
    //boolean isForViewType(int viewType);
    int getViewType();
    H onCreateViewHolder(ViewGroup parent, Consumer<Click<Integer>> clickConsumer);
    void onBindViewHolder(AdapterDataSource<T> adapterDataSource, H holder, int position);
}
