package ru.startandroid.vocabulary.sentences.exerciselist.list;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.startandroid.vocabulary.R;
import ru.startandroid.vocabulary.data.exercise.Exercise;
import ru.startandroid.vocabulary.utils.click.AttachItemToClick;
import ru.startandroid.vocabulary.utils.click.Click;
import rx.Observable;

import static ru.startandroid.vocabulary.utils.click.Click.Type.ITEM_CLICK;
import static ru.startandroid.vocabulary.utils.click.Click.Type.ITEM_LONG_CLICK;

public class ExerciseHolder extends RecyclerView.ViewHolder {

    private View root;

    @BindView(R.id.name)
    TextView textViewName;

    private final Observable<Click<Exercise>> allClicks;
    private final AttachItemToClick<Exercise> attachToClick =  new AttachItemToClick<>(ITEM_CLICK);
    private final AttachItemToClick<Exercise> attachToLongClick =  new AttachItemToClick<>(ITEM_LONG_CLICK);


    public ExerciseHolder(View itemView) {
        super(itemView);
        root = itemView;
        initView(root);

        Observable<Click<Exercise>> clicks = RxView.clicks(itemView)
                .map(attachToClick);
        Observable<Click<Exercise>> longClicks = RxView.longClicks(itemView)
                .map(attachToLongClick);
        allClicks = clicks.mergeWith(longClicks);
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);
    }

    public void bind(Exercise exercise) {
        attachToClick.setItem(exercise);
        attachToLongClick.setItem(exercise);

        textViewName.setText(exercise.getName());
        if (exercise.isEnabled()) {
            textViewName.setTypeface(null, Typeface.BOLD);
        } else {
            textViewName.setTypeface(null, Typeface.NORMAL);
        }
    }

    public Observable<Click<Exercise>> getClicks() {
        return allClicks;
    }

}
