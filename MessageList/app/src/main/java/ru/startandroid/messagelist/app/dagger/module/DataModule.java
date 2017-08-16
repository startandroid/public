package ru.startandroid.messagelist.app.dagger.module;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.annotation.Retention;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import ru.startandroid.messagelist.app.dagger.scope.AppScope;
import ru.startandroid.messagelist.data.database.DBHelper;
import ru.startandroid.messagelist.data.database.ItemDatabaseDataSource;
import ru.startandroid.messagelist.data.database.ItemDatabaseDataSourceImpl;
import ru.startandroid.messagelist.data.database.ItemDatabaseDataSourceRx;
import ru.startandroid.messagelist.data.database.ItemDatabaseDataSourceRxImpl;
import ru.startandroid.messagelist.data.database.MessagesTable;
import ru.startandroid.messagelist.data.messages.DbMessageMapper;
import ru.startandroid.messagelist.data.messages.MessageDataSourceLocal;
import ru.startandroid.messagelist.data.messages.MessageDataSourceLocalImpl;
import ru.startandroid.messagelist.data.messages.MessageDataSourceRemote;
import ru.startandroid.messagelist.data.messages.MessageDataSourceRemoteImpl;
import ru.startandroid.messagelist.data.messages.SqlSpecificationMessageFactory;
import ru.startandroid.messagelist.data.webapi.MessagesApi;
import ru.startandroid.messagelist.domain.messages.DbMessage;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Module(includes = {NetworkModule.class, MessageRepositoryModule.class})
public class DataModule {

    @AppScope
    @Provides
    MessageDataSourceLocal provideMessageDataSourceLocal(ItemDatabaseDataSourceRx<DbMessage> itemDatabaseDataSourceRx, SqlSpecificationMessageFactory sqlSpecificationMessageFactory) {
        return new MessageDataSourceLocalImpl(itemDatabaseDataSourceRx, sqlSpecificationMessageFactory);
    }

    @AppScope
    @Provides
    MessageDataSourceRemote provideMessageDataSourceRemote(MessagesApi messagesApi) {
        return new MessageDataSourceRemoteImpl(messagesApi);
    }


    @AppScope
    @Provides
    ItemDatabaseDataSourceRx<DbMessage> provideMessageDatabaseDataSourceRx(ItemDatabaseDataSource<DbMessage> itemDatabaseDataSource) {
        return new ItemDatabaseDataSourceRxImpl<>(itemDatabaseDataSource);
    }

    @AppScope
    @Provides
    ItemDatabaseDataSource<DbMessage> provideMessageDatabaseDataSource(SQLiteOpenHelper sqLiteOpenHelper, DbMessageMapper itemMapper) {
        return new ItemDatabaseDataSourceImpl<DbMessage>(sqLiteOpenHelper, itemMapper, MessagesTable.TABLE_NAME);
    }

    @AppScope
    @Provides
    SQLiteOpenHelper provideSQLiteOpenHelper(Context context) {
        return new DBHelper(context);
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

    @Qualifier
    @Retention(RUNTIME)
    public @interface DatabaseScheduler {
    }

}
