package ru.startandroid.vocabulary.verbs.test.dagger;

import dagger.Subcomponent;
import ru.startandroid.vocabulary.dictionary.test.dagger.TestActivityModule;
import ru.startandroid.vocabulary.dictionary.test.dagger.TestActivityScope;
import ru.startandroid.vocabulary.dictionary.test.ui.TestActivity;
import ru.startandroid.vocabulary.verbs.test.ui.TestVerbActivity;

@TestVerbActivityScope
@Subcomponent(modules = TestVerbActivityModule.class)
public interface TestVerbActivityComponent {
    void injectTestVerbActivity(TestVerbActivity testActivity);
}
