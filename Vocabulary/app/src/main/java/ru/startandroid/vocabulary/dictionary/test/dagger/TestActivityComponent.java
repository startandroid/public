package ru.startandroid.vocabulary.dictionary.test.dagger;

import dagger.Subcomponent;
import ru.startandroid.vocabulary.dictionary.test.ui.TestActivity;

@TestActivityScope
@Subcomponent(modules = TestActivityModule.class)
public interface TestActivityComponent {
    void injectTestActivity(TestActivity testActivity);
}
