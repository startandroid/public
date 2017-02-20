package ru.startandroid.vocabulary.dictionary.recordlist.list;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.startandroid.vocabulary.R;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.utils.click.AttachItemToClick;
import ru.startandroid.vocabulary.utils.click.Click;
import rx.Observable;

import static ru.startandroid.vocabulary.utils.click.Click.Type.ITEM_CLICK;
import static ru.startandroid.vocabulary.utils.click.Click.Type.ITEM_LONG_CLICK;

/**
 * Created by Damager on 02.02.2017.
 */

public class RecordHolder extends RecyclerView.ViewHolder {

    private View root;

    @BindView(R.id.word)
    TextView textViewWord;

    @BindView(R.id.translate)
    TextView textViewTranslate;

    @BindView(R.id.definition)
    TextView textViewDefinition;

    private final Observable<Click<Record>> allClicks;
    private final AttachItemToClick<Record> attachToClick =  new AttachItemToClick<>(ITEM_CLICK);
    private final AttachItemToClick<Record> attachToLongClick =  new AttachItemToClick<>(ITEM_LONG_CLICK);


    public RecordHolder(View itemView) {
        super(itemView);
        root = itemView;
        initView(root);

        Observable<Click<Record>> clicks = RxView.clicks(itemView)
                .map(attachToClick);
        Observable<Click<Record>> longClicks = RxView.longClicks(itemView)
                .map(attachToLongClick);
        allClicks = clicks.mergeWith(longClicks);
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);
    }

    public void bind(Record record) {
        attachToClick.setItem(record);
        attachToLongClick.setItem(record);

        // TODO
        textViewWord.setText(record.getWord() + " (" + record.getRememberedCount() + ")");
        textViewTranslate.setText(record.getTranslate());
        textViewTranslate.setVisibility(TextUtils.isEmpty(record.getTranslate()) ? View.GONE : View.VISIBLE);
        textViewDefinition.setText(record.getDefinition());
        textViewDefinition.setVisibility(TextUtils.isEmpty(record.getDefinition()) ? View.GONE : View.VISIBLE);
    }

    public Observable<Click<Record>> getClicks() {
        return allClicks;
    }

}
