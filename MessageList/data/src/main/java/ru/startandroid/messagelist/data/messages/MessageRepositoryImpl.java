package ru.startandroid.messagelist.data.messages;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import ru.startandroid.messagelist.domain.messages.DbMessage;
import ru.startandroid.messagelist.domain.messages.MessageRepository;

public class MessageRepositoryImpl implements MessageRepository {

    private final MessageDataSourceLocal messageDataSourceLocal;
    private final MessageDataSourceRemote messageDataSourceRemote;

    private final Scheduler networkScheduler;
    private final Scheduler dbScheduler;
    private final Scheduler observeScheduler;
    private final DbMessageMapper dbMessageMapper;

    public MessageRepositoryImpl(MessageDataSourceLocal messageDataSourceLocal, MessageDataSourceRemote messageDataSourceRemote,
                                 Scheduler networkScheduler, Scheduler dbScheduler, Scheduler observeScheduler, DbMessageMapper dbMessageMapper) {
        this.messageDataSourceLocal = messageDataSourceLocal;
        this.messageDataSourceRemote = messageDataSourceRemote;
        this.networkScheduler = networkScheduler;
        this.dbScheduler = dbScheduler;
        this.observeScheduler = observeScheduler;
        this.dbMessageMapper = dbMessageMapper;
    }

    @Override
    public Observable<List<DbMessage>> getMessages(int page) {
        Observable<List<DbMessage>> localMessages = messageDataSourceLocal
                .getMessages(page)
                .filter(messages -> messages != null && messages.size() > 0)
                .subscribeOn(dbScheduler);

        Observable<List<DbMessage>> remoteMessages = Observable.defer(() -> messageDataSourceRemote
                .getMessages(page)
                .subscribeOn(networkScheduler)
                .map(dbMessageMapper::mapToDbMessages)
                .observeOn(dbScheduler)
                .doOnNext(messages -> messageDataSourceLocal.insertMessages(messages).subscribe())
                .observeOn(networkScheduler));

        return Observable.concat(localMessages, remoteMessages)
                .take(1)
                .observeOn(observeScheduler);
    }

    @Override
    public Completable updateMessageFavorite(long id, boolean favorite) {
        return messageDataSourceLocal
                .updateMessageFavorite(id, favorite)
                .subscribeOn(dbScheduler)
                .observeOn(observeScheduler);
    }


}
