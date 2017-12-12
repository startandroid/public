package ru.startandroid.vocabulary.base;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.startandroid.vocabulary.storage.database.ItemDatabaseRepository;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationRaw;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationUpdate;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhere;
import rx.Completable;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public abstract class ItemController<I> {

    private final ItemDatabaseRepository<I> itemRepository;
    private final Scheduler dbScheduler;
    private final ItemMapper<I> itemMapper;

    public ItemController(ItemDatabaseRepository<I> itemRepository, Scheduler dbScheduler, ItemMapper<I> itemMapper) {
        this.itemRepository = itemRepository;
        this.dbScheduler = dbScheduler;
        this.itemMapper = itemMapper;
    }


    public Observable<List<I>> getItems(SqlSpecificationRaw sqlSpecificationRaw) {
        return Observable.just(sqlSpecificationRaw)
                .map(new Func1<SqlSpecificationRaw, List<I>>() {
                    @Override
                    public List<I> call(SqlSpecificationRaw sqlSpecificationRaw) {
                        return itemRepository.query(sqlSpecificationRaw);
                    }
                })
                .subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable addItem(I item) {
        return Observable.just(item)
                .map(new Func1<I, Void>() {
                    @Override
                    public Void call(I item) {
                        itemRepository.insert(Arrays.asList(item));
                        return null;
                    }
                })
                .subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }


    public Completable updateItem(I item) {
        ContentValues cv = itemMapper.toContentValues(item);
        SqlSpecificationWhere specificationWhere = createSqlSpecificationWhereFromItem(item);// new SqlSpecificationWhereByValue(RecordsTable.ID, Long.toString(item.getExerciseId()));
        SqlSpecificationUpdate sqlSpecificationUpdate = new SqlSpecificationUpdate(cv, specificationWhere);
        return update(sqlSpecificationUpdate);
    }

    public Completable update(SqlSpecificationUpdate sqlSpecificationUpdate) {
        List<SqlSpecificationUpdate> list = new ArrayList<>(1);
        list.add(sqlSpecificationUpdate);
        return update(list);
    }

    public Completable update(List<SqlSpecificationUpdate> sqlSpecificationUpdateSet) {
        return Observable.just(sqlSpecificationUpdateSet)
                .map(new Func1<List<SqlSpecificationUpdate>, Void>() {
                    @Override
                    public Void call(List<SqlSpecificationUpdate> specificationUpdateSet) {
                        itemRepository.beginTransaction();
                        try {
                            for (SqlSpecificationUpdate sqlSpecificationUpdate : specificationUpdateSet) {
                                itemRepository.update(sqlSpecificationUpdate, false);
                            }
                            itemRepository.setTransactionSuccessful();
                        } finally {
                            itemRepository.endTransaction();
                            itemRepository.postUpdateEvent();
                        }
                        return null;
                    }
                })
                .subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }


    abstract protected SqlSpecificationWhere createSqlSpecificationWhereFromItem(I item);


}
