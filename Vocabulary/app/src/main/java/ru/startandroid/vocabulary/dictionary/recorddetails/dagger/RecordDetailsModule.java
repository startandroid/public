package ru.startandroid.vocabulary.dictionary.recorddetails.dagger;

import android.os.Bundle;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.vocabulary.data.record.RecordController;
import ru.startandroid.vocabulary.dictionary.recorddetails.ui.RecordDetailsContract;
import ru.startandroid.vocabulary.dictionary.recorddetails.ui.RecordDetailsCreatePresenter;
import ru.startandroid.vocabulary.dictionary.recorddetails.ui.RecordDetailsEditPresenter;

@Module
public class RecordDetailsModule {

    private final Bundle args;

    public RecordDetailsModule(Bundle args) {
        this.args = args;
    }

    @RecordDetailsCreate
    @RecordDetailsScope
    @Provides
    RecordDetailsContract.Presenter provideRecordDetailsCreatePresenter(RecordController recordController) {
        return new RecordDetailsCreatePresenter(args, recordController);
    }

    @RecordDetailsEdit
    @RecordDetailsScope
    @Provides
    RecordDetailsContract.Presenter provideRecordDetailsEditPresenter(RecordController recordController) {
        return new RecordDetailsEditPresenter(args, recordController);
    }

}
