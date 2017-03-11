package ru.startandroid.vocabulary.app.dagger;

import dagger.Component;
import ru.startandroid.vocabulary.dictionary.recorddetails.dagger.RecordDetailsComponent;
import ru.startandroid.vocabulary.dictionary.recorddetails.dagger.RecordDetailsModule;
import ru.startandroid.vocabulary.dictionary.recordlist.dagger.RecordListComponent;
import ru.startandroid.vocabulary.storage.StorageModule;
import ru.startandroid.vocabulary.dictionary.test.dagger.TestActivityComponent;
import ru.startandroid.vocabulary.verbs.test.dagger.TestVerbActivityComponent;

@AppScope
@Component(modules = {AppModule.class, StorageModule.class})
public interface AppComponent {
    RecordListComponent createRecordListComponent();
    RecordDetailsComponent createRecordDetailsComponent(RecordDetailsModule recordDetailsModule);
    TestActivityComponent createTestActivityComponent();
    TestVerbActivityComponent createTestVerbActivityComponent();

}
