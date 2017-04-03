package ru.startandroid.vocabulary.sentences.exerciselist.dagger;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.vocabulary.data.exercise.ExerciseController;
import ru.startandroid.vocabulary.events.EventBus;
import ru.startandroid.vocabulary.sentences.exerciselist.ui.ExerciseListContract;
import ru.startandroid.vocabulary.sentences.exerciselist.ui.ExerciseListPresenter;

@Module
public class ExerciseListModule {

    @ExerciseListScope
    @Provides
    ExerciseListContract.Presenter provideExerciseListPresenter(ExerciseController controller, EventBus eventBus) {
        return new ExerciseListPresenter(controller, eventBus);
    }


}
