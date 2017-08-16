package ru.startandroid.messagelist.ui.message.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import io.reactivex.functions.Consumer;
import ru.startandroid.messagelist.base.adapter.AdapterDataSource;
import ru.startandroid.messagelist.common.utils.clicks.Click;
import ru.startandroid.messagelist.data.message.UiMessage;
import ru.startandroid.messagelist.ui.message.list.adapter.delegate.AdapterDelegateMessage;
import ru.startandroid.messagelist.ui.message.list.adapter.delegate.AdapterDelegateLoading;
import ru.startandroid.messagelist.ui.message.list.adapter.delegate.AdapterDelegateTryAgain;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterDelegatesManager.Callback {

    private final AdapterDataSource<UiMessage> dataSource;
    private final AdapterDelegatesManager adapterDelegatesManager;
    private final Consumer<Click<Integer>> clickConsumer;
    private final Picasso picasso;

    public MessageAdapter(AdapterDataSource dataSource, Consumer<Click<Integer>> clickConsumer, Picasso picasso) {
        this.dataSource = dataSource;
        this.clickConsumer = clickConsumer;
        this.picasso = picasso;
        this.adapterDelegatesManager = new AdapterDelegatesManager(this);
        adapterDelegatesManager.addAdapterDelegate(new AdapterDelegateMessage(picasso));
        adapterDelegatesManager.addAdapterDelegate(new AdapterDelegateTryAgain());
        adapterDelegatesManager.addAdapterDelegate(new AdapterDelegateLoading());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return adapterDelegatesManager.onCreateViewHolder(parent, viewType, clickConsumer);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        adapterDelegatesManager.onBindViewHolder(dataSource, holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return adapterDelegatesManager.getItemViewType(dataSource, position);
    }

    @Override
    public int getItemCount() {
        return adapterDelegatesManager.getItemCount(dataSource);
    }

    public Consumer<Boolean> getLoadingConsumer() {
        return adapterDelegatesManager.getLoadingConsumer();
    }

    public Consumer<Boolean> getTryAgainConsumer() {
        return adapterDelegatesManager.getTryAgainConsumer();
    }

    public boolean isAvailableToLoad() {
        return adapterDelegatesManager.isAvailableToLoad();
    }

    @Override
    public void notifyChanges() {
        notifyDataSetChanged();
    }
}
