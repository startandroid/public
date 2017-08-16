package ru.startandroid.messagelist.domain.messages;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface MessageRepository {
    Observable<List<DbMessage>> getMessages(int page);
    Completable updateMessageFavorite(long id, boolean favorite);
}
