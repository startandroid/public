package ru.startandroid.vocabulary.storage;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.vocabulary.app.dagger.AppScope;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.data.record.storage.RecordMapper;
import ru.startandroid.vocabulary.events.EventBus;
import ru.startandroid.vocabulary.events.Events;
import ru.startandroid.vocabulary.storage.database.DBHelper;
import ru.startandroid.vocabulary.storage.database.DatabaseScheduler;
import ru.startandroid.vocabulary.storage.database.ItemDatabaseRepository;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.RecordsTable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@Module
public class StorageModule {

    @AppScope
    @Provides
    ItemDatabaseRepository<Record> provideMessageDatabaseRepository(SQLiteOpenHelper sqLiteOpenHelper, ItemMapper<Record> recordMapper, EventBus eventBus) {
        return new ItemDatabaseRepository<>(sqLiteOpenHelper, recordMapper, RecordsTable.TABLE_NAME, eventBus, Events.recordsUpdated());
    }

    @AppScope
    @Provides
    SQLiteOpenHelper provideSQLiteOpenHelper(Context context) {
        return new DBHelper(context);
    }

    @AppScope
    @Provides
    ItemMapper<Record> provideRecordMapper() {
        return new RecordMapper();
    }

    @AppScope
    @DatabaseScheduler
    @Provides
    Scheduler provideDbScheduler(@DatabaseScheduler Executor executor) {
        return Schedulers.from(executor);
    }

    @AppScope
    @DatabaseScheduler
    @Provides
    Executor provideDbExecutor() {
        return Executors.newSingleThreadExecutor();
    }

}
