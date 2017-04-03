package ru.startandroid.vocabulary.sentences.exerciselist.dagger;

import dagger.Subcomponent;
import ru.startandroid.vocabulary.sentences.exerciselist.ui.ExerciseListFragment;

@ExerciseListScope
@Subcomponent(modules = ExerciseListModule.class)
public interface ExerciseListComponent {
    void injectExerciseListFragment(ExerciseListFragment fragment);
}
