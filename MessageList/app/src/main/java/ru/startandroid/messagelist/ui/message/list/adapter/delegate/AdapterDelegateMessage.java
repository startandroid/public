package ru.startandroid.messagelist.ui.message.list.adapter.delegate;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import ru.startandroid.messagelist.R;
import ru.startandroid.messagelist.base.adapter.AdapterDataSource;
import ru.startandroid.messagelist.common.utils.clicks.AttachItemToClick;
import ru.startandroid.messagelist.common.utils.clicks.Click;
import ru.startandroid.messagelist.data.message.UiMessage;
import ru.startandroid.messagelist.ui.message.list.adapter.AdapterDelegatesManager;

import static ru.startandroid.messagelist.common.utils.clicks.Click.Type.ITEM;

public class AdapterDelegateMessage implements AdapterDelegate<UiMessage, AdapterDelegateMessage.MessageHolder> {

    private final Picasso picasso;

    public AdapterDelegateMessage(Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public int getViewType() {
        return AdapterDelegatesManager.ViewType.MESSAGE.ordinal();
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, Consumer<Click<Integer>> clickConsumer) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        return new MessageHolder(view, clickConsumer, picasso);
    }

    @Override
    public void onBindViewHolder(AdapterDataSource<UiMessage> adapterDataSource, MessageHolder holder, int position) {
        holder.bind(adapterDataSource.getItem(position));
    }

    static class MessageHolder extends RecyclerView.ViewHolder {

        private final AttachItemToClick<Integer> attachToClick = new AttachItemToClick<>(ITEM);
        private final BehaviorSubject<String> imageUrl;

        @BindView(R.id.text)
        TextView textViewText;

        @BindView(R.id.photo)
        ImageView imageViewPhoto;

        @BindDimen(R.dimen.photo_height)
        int photoHeight;


        public MessageHolder(View itemView, Consumer<Click<Integer>> clickConsumer, Picasso picasso) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if (clickConsumer != null) {
                RxView.clicks(itemView)
                        .map(o -> getAdapterPosition())
                        .map(attachToClick)
                        .subscribe(clickConsumer);
            }

            imageUrl = BehaviorSubject.create();
            imageUrl.distinctUntilChanged()
                    .doOnNext(s -> imageViewPhoto.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE))
                    .filter(s -> !TextUtils.isEmpty(s))
                    .subscribe(s -> picasso.load(s).resize(0, photoHeight).error(R.mipmap.ic_launcher_round).into(imageViewPhoto));
        }

        public void bind(UiMessage message) {
            textViewText.setText(message.getText());
            textViewText.setTextColor(message.isFavorite() ? Color.BLACK : Color.GRAY);
            imageUrl.onNext(message.getImage() == null ? "" : message.getImage());
        }
    }

}
