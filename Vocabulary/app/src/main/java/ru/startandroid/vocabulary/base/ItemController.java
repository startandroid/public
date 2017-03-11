package ru.startandroid.vocabulary.base;

import android.content.ContentValues;

import java.util.Arrays;
import java.util.List;

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
        SqlSpecificationWhere specificationWhere = createSqlSpecificationWhereFromItem(item);// new SqlSpecificationWhereByValue(RecordsTable.ID, Long.toString(item.getId()));
        SqlSpecificationUpdate sqlSpecificationUpdate = new SqlSpecificationUpdate(cv, specificationWhere);
        return Observable.just(sqlSpecificationUpdate)
                .map(new Func1<SqlSpecificationUpdate, Void>() {
                    @Override
                    public Void call(SqlSpecificationUpdate specificationUpdate) {
                        itemRepository.update(specificationUpdate);
                        return null;
                    }
                })
                .subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }


    abstract protected SqlSpecificationWhere createSqlSpecificationWhereFromItem(I item);


}
