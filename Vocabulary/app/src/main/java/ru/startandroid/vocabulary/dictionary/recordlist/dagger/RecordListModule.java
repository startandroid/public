package ru.startandroid.vocabulary.dictionary.recordlist.dagger;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.vocabulary.data.record.RecordController;
import ru.startandroid.vocabulary.dictionary.recordlist.ui.RecordListContract;
import ru.startandroid.vocabulary.dictionary.recordlist.ui.RecordListPresenter;
import ru.startandroid.vocabulary.events.EventBus;

@Module
public class RecordListModule {

    @RecordListScope
    @Provides
    RecordListContract.Presenter provideRecordListPresenter(RecordController recordController, EventBus eventBus) {
        return new RecordListPresenter(recordController, eventBus);
    }


}
