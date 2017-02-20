package ru.startandroid.vocabulary.test.dagger;

import dagger.Subcomponent;
import ru.startandroid.vocabulary.test.ui.TestActivity;

@TestActivityScope
@Subcomponent(modules = TestActivityModule.class)
public interface TestActivityComponent {
    void injectTestActivity(TestActivity testActivity);
}
