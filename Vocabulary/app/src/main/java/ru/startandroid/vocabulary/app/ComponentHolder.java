package ru.startandroid.vocabulary.app;

import android.content.Context;
import android.os.Bundle;

import ru.startandroid.vocabulary.app.dagger.AppComponent;
import ru.startandroid.vocabulary.app.dagger.AppModule;
import ru.startandroid.vocabulary.app.dagger.DaggerAppComponent;
import ru.startandroid.vocabulary.dictionary.recorddetails.dagger.RecordDetailsComponent;
import ru.startandroid.vocabulary.dictionary.recorddetails.dagger.RecordDetailsModule;
import ru.startandroid.vocabulary.dictionary.recordlist.dagger.RecordListComponent;
import ru.startandroid.vocabulary.test.dagger.TestActivityComponent;
import ru.startandroid.vocabulary.test.ui.TestActivity;

public class ComponentHolder {

    Context context;

    AppComponent appComponent;

    RecordListComponent recordListComponent;
    RecordDetailsComponent recordDetailsComponent;
    TestActivityComponent testActivityComponent;

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

}
