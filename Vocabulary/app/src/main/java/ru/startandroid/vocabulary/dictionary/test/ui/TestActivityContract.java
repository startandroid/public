package ru.startandroid.vocabulary.dictionary.test.ui;

import ru.startandroid.vocabulary.base.mvp.MvpPresenter;
import ru.startandroid.vocabulary.base.mvp.MvpView;
import ru.startandroid.vocabulary.data.record.Record;

public interface TestActivityContract {

    interface View extends MvpView {
        void showRecord(Record record);
        void closeScreen();
    }


    interface Presenter extends MvpPresenter<View> {
        void showData();
        void onYesClick();
        void onNoClick();

    }

}
