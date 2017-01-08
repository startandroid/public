package ru.startandroid.messagelist.storage;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.messagelist.app.AppScope;
import ru.startandroid.messagelist.data.message.Message;
import ru.startandroid.messagelist.data.message.storage.MessageMapper;
import ru.startandroid.messagelist.events.EventBus;
import ru.startandroid.messagelist.events.Events;
import ru.startandroid.messagelist.storage.database.DBHelper;
import ru.startandroid.messagelist.storage.database.DatabaseScheduler;
import ru.startandroid.messagelist.storage.database.ItemDatabaseRepository;
import ru.startandroid.messagelist.storage.database.ItemMapper;
import ru.startandroid.messagelist.storage.database.MessagesTable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@Module
public class StorageModule {

    @AppScope
    @Provides
    ItemDatabaseRepository<Message> provideMessageDatabaseRepository(SQLiteOpenHelper sqLiteOpenHelper, ItemMapper<Message> messageMapper, EventBus eventBus) {
        return new ItemDatabaseRepository<>(sqLiteOpenHelper, messageMapper, MessagesTable.TABLE_NAME, eventBus, Events.messagesUpdated());
    }

    @AppScope
    @Provides
    SQLiteOpenHelper provideSQLiteOpenHelper(Context context) {
        return new DBHelper(context);
    }

    @AppScope
    @Provides
    ItemMapper<Message> provideMessageMapper() {
        return new MessageMapper();
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

    @AppScope
    @Provides
    Preferences providePreferences(Context context) {
        return new Preferences(context);
    }


}
