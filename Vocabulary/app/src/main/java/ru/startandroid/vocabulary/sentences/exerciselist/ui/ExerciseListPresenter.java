package ru.startandroid.vocabulary.sentences.exerciselist.ui;

import java.util.ArrayList;
import java.util.List;

import ru.startandroid.vocabulary.base.mvp.PresenterBase;
import ru.startandroid.vocabulary.data.exercise.Exercise;
import ru.startandroid.vocabulary.data.exercise.ExerciseController;
import ru.startandroid.vocabulary.data.exercise.storage.SqlSpecificationRawAllExercises;
import ru.startandroid.vocabulary.events.Event;
import ru.startandroid.vocabulary.events.EventBus;
import ru.startandroid.vocabulary.events.EventType;
import ru.startandroid.vocabulary.events.Events;
import rx.Subscription;
import rx.functions.Action1;

public class ExerciseListPresenter extends PresenterBase<ExerciseListContract.View> implements ExerciseListContract.Presenter {

    private final ExerciseController exerciseController;
    private final EventBus eventBus;

    private boolean notLoadedYet = true;
    private List<Exercise> data = new ArrayList<>();

    private Subscription loadSubscription;


    public ExerciseListPresenter(ExerciseController exerciseController, EventBus eventBus) {
        this.exerciseController = exerciseController;
        this.eventBus = eventBus;
        addSubscription(eventBus.getEventsObservable(EventType.EXERCISES_UPDATED).subscribe(new Action1<Event>() {
            @Override
            public void call(Event event) {
                loadData();
            }
        }));
    }

    @Override
    public void showData() {
        if (notLoadedYet) {
            loadData();
        } else {
            showDataToView();
        }

    }

    private void showDataToView() {
        if (isViewAttached()) {
            getView().setData(data);
        }
    }

    private void loadData() {
        notLoadedYet = false;
        removeSubscription(loadSubscription);
        loadSubscription = exerciseController.getItems(new SqlSpecificationRawAllExercises())
                .subscribe(new Action1<List<Exercise>>() {
                    @Override
                    public void call(List<Exercise> exercises) {
                        data = exercises;
                        showDataToView();
                        getView().setData(exercises);
                    }
                });
        addSubscription(loadSubscription);
    }

    @Override
    public void onExerciseClick(Exercise exercise) {
        exercise.setEnabled(!exercise.isEnabled());
        exerciseController.updateItem(exercise).subscribe();
    }
}
