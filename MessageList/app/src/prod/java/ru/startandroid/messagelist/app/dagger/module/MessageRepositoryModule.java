package ru.startandroid.messagelist.app.dagger.module;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import ru.startandroid.messagelist.app.dagger.scope.AppScope;
import ru.startandroid.messagelist.data.database.ItemDatabaseDataSourceRx;
import ru.startandroid.messagelist.data.messages.DbMessageMapper;
import ru.startandroid.messagelist.data.messages.MessageDataSourceLocal;
import ru.startandroid.messagelist.data.messages.MessageDataSourceLocalImpl;
import ru.startandroid.messagelist.data.messages.MessageDataSourceRemote;
import ru.startandroid.messagelist.data.messages.MessageDataSourceRemoteImpl;
import ru.startandroid.messagelist.data.messages.MessageRepositoryImpl;
import ru.startandroid.messagelist.data.messages.SqlSpecificationMessageFactory;
import ru.startandroid.messagelist.data.webapi.MessagesApi;
import ru.startandroid.messagelist.domain.messages.DbMessage;
import ru.startandroid.messagelist.domain.messages.MessageRepository;

@Module
public class MessageRepositoryModule {

    @AppScope
    @Provides
    MessageRepository provideMessageRepository(MessageDataSourceLocal messageDataSourceLocal, MessageDataSourceRemote messageDataSourceRemote,
                                               @NetworkModule.NetworkScheduler Scheduler networkScheduler, @DataModule.DatabaseScheduler Scheduler dbScheduler,
                                               @AppModule.UIScheduler Scheduler observeScheduler, DbMessageMapper dbMessageMapper) {
        return new MessageRepositoryImpl(messageDataSourceLocal, messageDataSourceRemote, networkScheduler, dbScheduler, observeScheduler, dbMessageMapper);
    }


}
