package ru.startandroid.messagelist.ui.message.list.adapter.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.functions.Consumer;
import ru.startandroid.messagelist.R;
import ru.startandroid.messagelist.base.adapter.AdapterDataSource;
import ru.startandroid.messagelist.common.utils.clicks.AttachItemToClick;
import ru.startandroid.messagelist.common.utils.clicks.Click;
import ru.startandroid.messagelist.data.message.UiMessage;
import ru.startandroid.messagelist.ui.message.list.adapter.AdapterDelegatesManager;

import static ru.startandroid.messagelist.common.utils.clicks.Click.Type.TRY_AGAIN;

public class AdapterDelegateTryAgain implements AdapterDelegate<UiMessage, AdapterDelegateTryAgain.TryAgainHolder> {

    @Override
    public int getViewType() {
        return AdapterDelegatesManager.ViewType.TRY_AGAIN.ordinal();
    }

    @Override
    public TryAgainHolder onCreateViewHolder(ViewGroup parent, Consumer<Click<Integer>> clickConsumer) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_try_again, parent, false);
        return new TryAgainHolder(view, clickConsumer);
    }

    @Override
    public void onBindViewHolder(AdapterDataSource<UiMessage> adapterDataSource, TryAgainHolder holder, int position) {
    }

    static class TryAgainHolder extends RecyclerView.ViewHolder {

        private final AttachItemToClick<Integer> attachToClick = new AttachItemToClick<>(TRY_AGAIN);

        public TryAgainHolder(View itemView, Consumer<Click<Integer>> clickConsumer) {
            super(itemView);

            if (clickConsumer != null) {
                RxView.clicks(itemView)
                        .map(o -> getAdapterPosition())
                        .map(attachToClick)
                        .subscribe(clickConsumer);
            }
        }

    }
}
