package ru.startandroid.messagelist.data.message;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.startandroid.messagelist.storage.Preferences;
import ru.startandroid.messagelist.storage.database.ItemDatabaseRepository;
import ru.startandroid.messagelist.storage.database.MessagesTable;
import ru.startandroid.messagelist.storage.database.specification.SqlSpecificationRaw;
import ru.startandroid.messagelist.storage.database.specification.SqlSpecificationWhereByValue;
import ru.startandroid.messagelist.storage.database.specification.SqlSpecificationWhereByValues;
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

    public Completable deleteMessage(String id) {
        return Observable.just(id)
                .map(new Func1<String, Void>() {
                    @Override
                    public Void call(String id) {
                        messageRepository.delete(new SqlSpecificationWhereByValue(MessagesTable.ID, id));
                        return null;
                    }
                })
                .subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }

    public Completable deleteMessage(Collection<String> ids) {
        return Observable.just(ids)
                .map(new Func1<Collection<String>, Void>() {
                    @Override
                    public Void call(Collection<String> ids) {
                        messageRepository.delete(new SqlSpecificationWhereByValues(MessagesTable.ID, ids));
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
