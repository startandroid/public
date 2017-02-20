package ru.startandroid.vocabulary.dictionary.recordlist.ui;

import java.util.Collection;

import ru.startandroid.vocabulary.base.mvp.MvpPresenter;
import ru.startandroid.vocabulary.base.mvp.MvpView;
import ru.startandroid.vocabulary.data.record.Record;

public interface RecordListContract {

    interface View extends MvpView {
        void setData(Collection<Record> records);
    }

    interface Presenter extends MvpPresenter<View> {
        void showData();
    }

}
