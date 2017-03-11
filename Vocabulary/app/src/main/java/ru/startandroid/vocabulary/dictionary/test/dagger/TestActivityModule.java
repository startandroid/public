package ru.startandroid.vocabulary.dictionary.test.dagger;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.vocabulary.data.record.RecordController;
import ru.startandroid.vocabulary.dictionary.test.ui.TestActivityContract;
import ru.startandroid.vocabulary.dictionary.test.ui.TestActivityPresenter;

@Module
public class TestActivityModule {

    @TestActivityScope
    @Provides
    TestActivityContract.Presenter provideTestActivityPresenter(RecordController recordController) {
        return new TestActivityPresenter(recordController);
    }

}
