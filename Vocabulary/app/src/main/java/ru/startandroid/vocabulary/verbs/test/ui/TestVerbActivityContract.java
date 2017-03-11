package ru.startandroid.vocabulary.verbs.test.ui;

import ru.startandroid.vocabulary.base.mvp.MvpPresenter;
import ru.startandroid.vocabulary.base.mvp.MvpView;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.data.verb.Verb;

public interface TestVerbActivityContract {

    interface View extends MvpView {
        void showRecord(Verb verb);
        void closeScreen();
    }


    interface Presenter extends MvpPresenter<View> {
        void showData();
        void onYesClick();
        void onNoClick();

    }

}
