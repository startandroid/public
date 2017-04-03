package ru.startandroid.vocabulary.app;

import android.content.Context;
import android.os.Bundle;

import ru.startandroid.vocabulary.app.dagger.AppComponent;
import ru.startandroid.vocabulary.app.dagger.AppModule;
import ru.startandroid.vocabulary.app.dagger.DaggerAppComponent;
import ru.startandroid.vocabulary.dictionary.recorddetails.dagger.RecordDetailsComponent;
import ru.startandroid.vocabulary.dictionary.recorddetails.dagger.RecordDetailsModule;
import ru.startandroid.vocabulary.dictionary.recordlist.dagger.RecordListComponent;
import ru.startandroid.vocabulary.dictionary.test.dagger.TestActivityComponent;
import ru.startandroid.vocabulary.sentences.exerciselist.dagger.ExerciseListComponent;
import ru.startandroid.vocabulary.sentences.test.dagger.TestSentencesFragmentComponent;
import ru.startandroid.vocabulary.verbs.test.dagger.TestVerbActivityComponent;

public class ComponentHolder {

    Context context;

    AppComponent appComponent;

    RecordListComponent recordListComponent;
    RecordDetailsComponent recordDetailsComponent;
    TestActivityComponent testActivityComponent;
    TestVerbActivityComponent testVerbActivityComponent;
    ExerciseListComponent exerciseListComponent;
    TestSentencesFragmentComponent testSentencesFragmentComponent;


    public ComponentHolder(Context context) {
        this.context = context;
    }

    void init() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(context)).build();
    }

    // RecordListComponent

    public RecordListComponent getRecordListComponent() {
        if (recordListComponent == null) {
            recordListComponent = appComponent.createRecordListComponent();
        }
        return recordListComponent;
    }

    public void releaseRecordListComponent() {
        recordListComponent = null;
    }


    // RecordDetailsComponent

    public RecordDetailsComponent getRecordDetailsComponent(Bundle args) {
        if (recordDetailsComponent == null) {
            recordDetailsComponent = appComponent.createRecordDetailsComponent(new RecordDetailsModule(args));
        }
        return recordDetailsComponent;
    }

    public void releaseRecordDetailsComponent() {
        recordDetailsComponent = null;
    }


    // TestActivity

    public TestActivityComponent getTestActivityComponent() {
        if (testActivityComponent == null) {
            testActivityComponent = appComponent.createTestActivityComponent();
        }
        return testActivityComponent;
    }

    public void releaseTestActivityComponent() {
        testActivityComponent = null;
    }


    // TestVerbActivity

    public TestVerbActivityComponent getTestVerbActivityComponent() {
        if (testVerbActivityComponent == null) {
            testVerbActivityComponent = appComponent.createTestVerbActivityComponent();
        }
        return testVerbActivityComponent;
    }

    public void releaseTestVerbActivityComponent() {
        testVerbActivityComponent = null;
    }


    // ExerciseListComponent
    public ExerciseListComponent getExerciseListComponent() {
        if (exerciseListComponent == null) {
            exerciseListComponent = appComponent.createExerciseListComponent();
        }
        return exerciseListComponent;
    }

    public void releaseExerciseListComponent() {
        exerciseListComponent = null;
    }

    // TestSentencesFragmentComponent
    public TestSentencesFragmentComponent getTestSentencesFragmentComponent() {
        if (testSentencesFragmentComponent == null) {
            testSentencesFragmentComponent = appComponent.createTestSentencesFragmentComponent();
        }
        return testSentencesFragmentComponent;
    }

    public void releaseTestSentencesComponent() {
        testSentencesFragmentComponent = null;
    }

}
