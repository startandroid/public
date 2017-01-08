package ru.startandroid.messagelist.data.message;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import ru.startandroid.messagelist.app.Constants;
import ru.startandroid.messagelist.storage.Preferences;
import ru.startandroid.messagelist.storage.database.ItemDatabaseRepository;
import ru.startandroid.messagelist.storage.database.MessagesTable;
import ru.startandroid.messagelist.storage.database.specification.SqlSpecificationRaw;
import ru.startandroid.messagelist.storage.database.specification.SqlSpecificationUpdate;
import ru.startandroid.messagelist.storage.database.specification.SqlSpecificationWhere;
import ru.startandroid.messagelist.storage.database.specification.SqlSpecificationWhereByValue;
import ru.startandroid.messagelist.storage.database.specification.SqlSpecificationWhereByValues;
import ru.startandroid.messagelist.utils.CollectionUtils;
import ru.startandroid.messagelist.web.ApiService;
import rx.Completable;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MessageController {

    private final ItemDatabaseRepository<Message> messageRepository;
    private final ApiService apiService;
    private final Scheduler dbScheduler;
    private final Preferences preferences;

    public MessageController(ItemDatabaseRepository<Message> messageRepository, ApiService apiService, Scheduler dbScheduler, Preferences preferences) {
        this.messageRepository = messageRepository;
        this.apiService = apiService;
        this.dbScheduler = dbScheduler;
        this.preferences = preferences;
    }

    public Observable<List<Message>> getMessages(SqlSpecificationRaw sqlSpecificationRaw) {
        return Observable.just(sqlSpecificationRaw)
                .map(new Func1<SqlSpecificationRaw, List<Message>>() {
                    @Override
                    public List<Message> call(SqlSpecificationRaw sqlSpecificationRaw) {

                        return messageRepository.query(sqlSpecificationRaw);
                    }
                })
                .subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable markDeleted(Collection<Long> ids, boolean deleted) {
        ContentValues cv = new ContentValues(1);
        cv.put(MessagesTable.DELETED, deleted ? Constants.DATABASE_TRUE : Constants.DATABASE_FALSE);
        return updateMessages(ids, cv);
    }

    private Completable updateMessages(Collection<Long> ids, ContentValues cv) {
        List<String> stringIds = new ArrayList<>(ids.size());
        for (Long id : ids) {
            stringIds.add(Long.toString(id));
        }
        SqlSpecificationUpdate sqlSpecificationUpdate = new SqlSpecificationUpdate(cv, new SqlSpecificationWhereByValues(MessagesTable.ID, stringIds));

        return Observable.just(sqlSpecificationUpdate)
                .map(new Func1<SqlSpecificationUpdate, Void>() {
                    @Override
                    public Void call(SqlSpecificationUpdate sqlSpecificationUpdate) {
                        messageRepository.update(sqlSpecificationUpdate);
                        return null;
                    }
                })
                .subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }

    public Completable deleteMarkedMessages() {
        SqlSpecificationWhere specification = new SqlSpecificationWhereByValue(MessagesTable.DELETED, Integer.toString(Constants.DATABASE_TRUE));
        return Observable.just(specification)
                .map(new Func1<SqlSpecificationWhere, Void>() {
                    @Override
                    public Void call(SqlSpecificationWhere specification) {
                        messageRepository.delete(specification);
                        return null;
                    }
                })
                .subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }

    public Completable loadMessages() {
        int page = preferences.getLastPage();
        SaveNewMessagesToDbAction saveNewMessagesToDbAction = new SaveNewMessagesToDbAction(page);
        return apiService.messages(page)
                .map(new Func1<List<Message>, List<Message>>() {
                    @Override
                    public List<Message> call(List<Message> messages) {

                        try {
                            TimeUnit.SECONDS.sleep(7);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        return messages;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(dbScheduler)
                .doOnNext(saveNewMessagesToDbAction)
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }

    private class SaveNewMessagesToDbAction implements Action1<List<Message>> {

        private final int page;

        SaveNewMessagesToDbAction(int page) {
            this.page = page;
        }

        @Override
        public void call(List<Message> messages) {
            preferences.setLastPage(page + 1);
            messageRepository.insert(messages);
        }
    }

}
