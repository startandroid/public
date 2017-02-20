package ru.startandroid.vocabulary.dictionary.recorddetails.ui;

import ru.startandroid.vocabulary.base.mvp.MvpPresenter;
import ru.startandroid.vocabulary.base.mvp.MvpView;
import ru.startandroid.vocabulary.data.record.Record;

public interface RecordDetailsContract {

    interface View extends MvpView {
        void fillRecord(Record record);
        void closeScreen();
        void showRecord(Record record);

    }

    interface Presenter extends MvpPresenter<View> {
        void onSaveClick();
        void showData();
    }

}
