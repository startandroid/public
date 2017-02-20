package ru.startandroid.vocabulary.dictionary.recorddetails.ui;

import android.os.Bundle;

import ru.startandroid.vocabulary.base.mvp.PresenterBase;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.data.record.RecordController;

public class RecordDetailsCreatePresenter extends PresenterBase<RecordDetailsContract.View> implements RecordDetailsContract.Presenter {

    private final RecordController recordController;
    private final Bundle args;
    private Record record;

    public RecordDetailsCreatePresenter(Bundle args, RecordController recordController) {
        this.args = args;
        this.recordController = recordController;
    }

    @Override
    public void onSaveClick() {
        record = new Record();
        record.setAdded(System.currentTimeMillis());
        getView().fillRecord(record);
        recordController.addRecord(record).subscribe();
        getView().closeScreen();
    }

    @Override
    public void showData() {
        // do nothing
    }
}
