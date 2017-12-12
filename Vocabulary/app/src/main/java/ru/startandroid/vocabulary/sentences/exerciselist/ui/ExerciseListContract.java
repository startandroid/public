package ru.startandroid.vocabulary.sentences.exerciselist.ui;

import java.util.Collection;

import ru.startandroid.vocabulary.base.mvp.MvpPresenter;
import ru.startandroid.vocabulary.base.mvp.MvpView;
import ru.startandroid.vocabulary.data.exercise.Exercise;
import ru.startandroid.vocabulary.data.record.Record;

public interface ExerciseListContract {

    interface View extends MvpView {
        void setData(Collection<Exercise> data);
    }

    interface Presenter extends MvpPresenter<View> {
        void showData();
        void onExerciseClick(Exercise exercise);
        void select(String from, String to);
    }

}
