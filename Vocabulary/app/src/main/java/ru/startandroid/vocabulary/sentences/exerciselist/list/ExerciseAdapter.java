package ru.startandroid.vocabulary.sentences.exerciselist.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.startandroid.vocabulary.R;
import ru.startandroid.vocabulary.data.exercise.Exercise;
import ru.startandroid.vocabulary.utils.click.Click;
import rx.Observable;
import rx.subjects.PublishSubject;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseHolder> {

    private List<Exercise> data = new ArrayList<>();
    private Context context;
    private final PublishSubject<Click<Exercise>> onClickSubject = PublishSubject.create();

    public ExerciseAdapter(Context context) {
        this.context = context;
        setHasStableIds(true);
    }

    public void setData(Collection<Exercise> exercises) {
        data.clear();
        data.addAll(exercises);
        notifyDataSetChanged();
    }

    @Override
    public ExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exercise_list_item, parent, false);
        ExerciseHolder holder = new ExerciseHolder(view);
        holder.getClicks().subscribe(onClickSubject);
        return holder;
    }

    @Override
    public void onBindViewHolder(ExerciseHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private Exercise getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    public Observable<Click<Exercise>> getClicks() {
        return onClickSubject.asObservable();
    }
}
