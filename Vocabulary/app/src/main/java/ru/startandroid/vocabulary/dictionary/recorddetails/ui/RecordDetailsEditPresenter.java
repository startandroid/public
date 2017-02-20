package ru.startandroid.vocabulary.dictionary.recorddetails.ui;

import android.os.Bundle;

import ru.startandroid.vocabulary.app.Constants;
import ru.startandroid.vocabulary.base.mvp.PresenterBase;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.data.record.RecordController;

public class RecordDetailsEditPresenter extends PresenterBase<RecordDetailsContract.View>
        implements RecordDetailsContract.Presenter {

    private final RecordController recordController;
    private final Bundle args;
    private Record record;

    public RecordDetailsEditPresenter(Bundle args, RecordController recordController) {
        this.args = args;
        this.recordController = recordController;
        record = args.getParcelable(Constants.EXTRA_RECORD);
    }



    @Override
    public void onSaveClick() {
        getView().fillRecord(record);
        recordController.updateRecord(record).subscribe();
        getView().closeScreen();
    }

    @Override
    public void showData() {
        getView().showRecord(record);
    }
}
