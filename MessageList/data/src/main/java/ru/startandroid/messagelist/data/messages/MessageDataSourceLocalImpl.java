package ru.startandroid.messagelist.data.messages;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ru.startandroid.messagelist.data.database.ItemDatabaseDataSourceRx;
import ru.startandroid.messagelist.domain.messages.DbMessage;

public class MessageDataSourceLocalImpl implements MessageDataSourceLocal {

    private final ItemDatabaseDataSourceRx<DbMessage> messageDatabaseDataSource;
    private final SqlSpecificationMessageFactory sqlSpecificationMessageFactory;

    public MessageDataSourceLocalImpl(ItemDatabaseDataSourceRx<DbMessage> messageDatabaseDataSource, SqlSpecificationMessageFactory sqlSpecificationMessageFactory) {
        this.messageDatabaseDataSource = messageDatabaseDataSource;
        this.sqlSpecificationMessageFactory = sqlSpecificationMessageFactory;
    }

    @Override
    public Observable<List<DbMessage>> getMessages(int page) {
        return messageDatabaseDataSource.query(sqlSpecificationMessageFactory.getMessages(page));
    }

    @Override
    public Completable insertMessages(List<DbMessage> messages) {
        return messageDatabaseDataSource.insert(messages).ignoreElements();
    }

    @Override
    public Completable updateMessageFavorite(long id, boolean favorite) {
        return messageDatabaseDataSource.update(sqlSpecificationMessageFactory.updateMessageFavorite(id, favorite)).ignoreElements();
    }

}
