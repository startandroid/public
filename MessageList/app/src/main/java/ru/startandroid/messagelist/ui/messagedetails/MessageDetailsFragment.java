package ru.startandroid.messagelist.ui.messagedetails;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.startandroid.messagelist.R;
import ru.startandroid.messagelist.app.App;
import ru.startandroid.messagelist.app.Constants;
import ru.startandroid.messagelist.data.message.Message;
import ru.startandroid.messagelist.utils.FormatUtils;

public class MessageDetailsFragment extends Fragment {

    @Inject
    Picasso picasso;

    @BindView(R.id._id)
    TextView textViewId;
    @BindView(R.id.date)
    TextView textViewDate;
    @BindView(R.id.text)
    TextView textViewText;
    @BindView(R.id.image)
    ImageView imageViewImage;

    @BindDimen(R.dimen.product_photo_height)
    int photoHeight;

    @BindDimen(R.dimen.product_photo_height_full)
    int photoHeightFull;
    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            imageViewImage.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };
    private Unbinder unbinder;
    private boolean reCreate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(getActivity()).getComponentsHolder().getMessageDetailsComponent().injectView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_details_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Message message = getArguments().getParcelable(Constants.EXTRA_MESSAGE);
        setMessage(message);
    }

    private void setMessage(final Message message) {
        if (message == null) {
            return;
        }
        textViewId.setText(Long.toString(message.getId()));
        textViewDate.setText(FormatUtils.formatDateTime(message.getTime()));
        textViewText.setText(message.getText());

        if (!TextUtils.isEmpty(message.getImage())) {
            imageViewImage.setVisibility(View.VISIBLE);
            picasso.load(message.getImage()).resize(0, photoHeight).into(imageViewImage);
            picasso.load(message.getImage()).resize(0, photoHeight).into(imageViewImage, new Callback() {
                @Override
                public void onSuccess() {
                    picasso.load(message.getImage()).resize(0, photoHeightFull).into(target);
                }

                @Override
                public void onError() {

                }
            });

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        picasso.cancelRequest(imageViewImage);
        picasso.cancelRequest(target);
        target = null;
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        reCreate = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!reCreate) {
            App.get(getActivity()).getComponentsHolder().releaseMessageDetailsComponent();
        }
    }
}
