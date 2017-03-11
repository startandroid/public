package ru.startandroid.vocabulary.verbs.test.dagger;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.vocabulary.data.record.RecordController;
import ru.startandroid.vocabulary.data.verb.VerbController;
import ru.startandroid.vocabulary.dictionary.test.dagger.TestActivityScope;
import ru.startandroid.vocabulary.dictionary.test.ui.TestActivityContract;
import ru.startandroid.vocabulary.dictionary.test.ui.TestActivityPresenter;
import ru.startandroid.vocabulary.verbs.test.ui.TestVerbActivityContract;
import ru.startandroid.vocabulary.verbs.test.ui.TestVerbActivityPresenter;

@Module
public class TestVerbActivityModule {

    @TestVerbActivityScope
    @Provides
    TestVerbActivityContract.Presenter provideTestVerbActivityPresenter(VerbController verbController) {
        return new TestVerbActivityPresenter(verbController);
    }

}
