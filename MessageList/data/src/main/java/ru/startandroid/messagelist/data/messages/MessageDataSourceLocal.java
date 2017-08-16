package ru.startandroid.messagelist.data.messages;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ru.startandroid.messagelist.domain.messages.DbMessage;

public interface MessageDataSourceLocal {

    Observable<List<DbMessage>> getMessages(int page);

    Completable insertMessages(List<DbMessage> messages);

    Completable updateMessageFavorite(long id, boolean favorite);

}
