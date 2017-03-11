package ru.startandroid.vocabulary.app.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.data.record.RecordController;
import ru.startandroid.vocabulary.data.verb.Verb;
import ru.startandroid.vocabulary.data.verb.VerbController;
import ru.startandroid.vocabulary.events.EventBus;
import ru.startandroid.vocabulary.storage.database.DatabaseScheduler;
import ru.startandroid.vocabulary.storage.database.ItemDatabaseRepository;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import rx.Scheduler;

@Module
public class AppModule {

    Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @AppScope
    Context provideContext() {
        return context;
    }

    @Provides
    @AppScope
    RecordController provideRecordController(ItemDatabaseRepository<Record> recordRepository,
                                             @DatabaseScheduler Scheduler dbScheduler,
                                             ItemMapper<Record> recordMapper) {
        return new RecordController(recordRepository, dbScheduler, recordMapper);
    }

    @Provides
    @AppScope
    VerbController provideVerbController(ItemDatabaseRepository<Verb> verbRepository,
                                           @DatabaseScheduler Scheduler dbScheduler,
                                           ItemMapper<Verb> verbMapper) {
        return new VerbController(verbRepository, dbScheduler, verbMapper);
    }

    @AppScope
    @Provides
    EventBus provideEventBus() {
        return new EventBus();
    }
}
