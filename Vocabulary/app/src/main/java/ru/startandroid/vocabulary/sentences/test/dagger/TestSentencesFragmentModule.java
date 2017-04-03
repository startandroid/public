package ru.startandroid.vocabulary.sentences.test.dagger;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.vocabulary.data.sentence.SentenceController;
import ru.startandroid.vocabulary.sentences.test.ui.TestSentencesContract;
import ru.startandroid.vocabulary.sentences.test.ui.TestSentencesPresenter;

@Module
public class TestSentencesFragmentModule {

    @TestSentencesScope
    @Provides
    TestSentencesContract.Presenter provideTestSentencesPresenter(SentenceController sentenceController) {
        return new TestSentencesPresenter(sentenceController);
    }

}
