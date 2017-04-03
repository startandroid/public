package ru.startandroid.vocabulary.sentences.test.dagger;

import dagger.Subcomponent;
import ru.startandroid.vocabulary.sentences.test.ui.TestSentencesFragment;

@TestSentencesScope
@Subcomponent(modules = TestSentencesFragmentModule.class)
public interface TestSentencesFragmentComponent {
    void injectTestSentencesFragment(TestSentencesFragment fragment);
}
