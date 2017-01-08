package ru.startandroid.messagelist.ui.messagelist.list;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.startandroid.messagelist.R;
import ru.startandroid.messagelist.app.App;
import ru.startandroid.messagelist.data.message.Message;
import ru.startandroid.messagelist.utils.FormatUtils;
import ru.startandroid.messagelist.utils.clicks.AttachItemToClick;
import ru.startandroid.messagelist.utils.clicks.Click;
import rx.Observable;

import static ru.startandroid.messagelist.utils.clicks.Click.Type.ITEM_CLICK;
import static ru.startandroid.messagelist.utils.clicks.Click.Type.ITEM_LONG_CLICK;

public class MessageListHolder extends RecyclerView.ViewHolder {

    private final Picasso picasso;

    @BindView(R.id._id)
    public TextView textViewId;

    @BindView(R.id.date)
    public TextView textViewDate;

    @BindView(R.id.text)
    public TextView textViewText;

    @BindView(R.id.image)
    public ImageView imageViewImage;

    @BindDimen(R.dimen.product_photo_height)
    int photoHeight;

    private final Observable<Click<MessageListHolder>> allClicks;
    private final AttachItemToClick<MessageListHolder> attachToClick =  new AttachItemToClick<>(ITEM_CLICK);
    private final AttachItemToClick<MessageListHolder> attachToLongClick =  new AttachItemToClick<>(ITEM_LONG_CLICK);

    private final View root;
    private Message message;

    public MessageListHolder(View itemView, Picasso picasso) {
        super(itemView);
        root = itemView;
        this.picasso = picasso;
        ButterKnife.bind(this, itemView);

        attachToClick.setItem(this);
        attachToLongClick.setItem(this);

        Observable<Click<MessageListHolder>> clicks = RxView.clicks(itemView)
                .map(attachToClick);
        Observable<Click<MessageListHolder>> longClicks = RxView.longClicks(itemView)
                .map(attachToLongClick);
        allClicks = clicks.mergeWith(longClicks);
    }

    public void bind(Message message) {
        this.message = message;

        textViewId.setText(message.getId());
        textViewDate.setText(FormatUtils.formatDateTime(message.getTime()));
        textViewText.setText(message.getText());
        if (!TextUtils.isEmpty(message.getImage())) {
            imageViewImage.setVisibility(View.VISIBLE);
            imageViewImage.setImageDrawable(null);
            picasso.load(message.getImage()).resize(0, photoHeight).into(imageViewImage);
        } else {
            imageViewImage.setVisibility(View.GONE);
        }
    }

    public Message getMessage() {
        return message;
    }

    public Observable<Click<MessageListHolder>> getClicks() {
        return allClicks;
    }

    public void setChecked(boolean checked) {
        root.setActivated(checked);
    }
}
