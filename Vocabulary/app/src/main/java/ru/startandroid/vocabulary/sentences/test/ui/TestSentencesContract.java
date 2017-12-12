package ru.startandroid.vocabulary.sentences.test.ui;

import ru.startandroid.vocabulary.base.mvp.MvpPresenter;
import ru.startandroid.vocabulary.base.mvp.MvpView;
import ru.startandroid.vocabulary.data.sentence.Sentence;

public interface TestSentencesContract {

    interface View extends MvpView {
        void showSentence(Sentence sentence);
        void showCount(int count);
        void closeScreen();
    }

    interface Presenter extends MvpPresenter<View> {
        void next();
        void showAgain();
        void remove();
    }

}
