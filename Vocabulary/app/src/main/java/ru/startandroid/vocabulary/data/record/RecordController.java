package ru.startandroid.vocabulary.data.record;

import android.content.ContentValues;

import java.util.Arrays;
import java.util.List;

import ru.startandroid.vocabulary.storage.database.ItemDatabaseRepository;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.RecordsTable;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationRaw;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationUpdate;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhere;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhereByValue;
import rx.Completable;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class RecordController {

    private final ItemDatabaseRepository<Record> recordRepository;
    private final Scheduler dbScheduler;
    private final ItemMapper<Record> recordMapper;

    public RecordController(ItemDatabaseRepository<Record> recordRepository, Scheduler dbScheduler, ItemMapper<Record> recordMapper) {
        this.recordRepository = recordRepository;
        this.dbScheduler = dbScheduler;
        this.recordMapper = recordMapper;
    }


    public Observable<List<Record>> getRecords(SqlSpecificationRaw sqlSpecificationRaw) {
        return Observable.just(sqlSpecificationRaw)
                .map(new Func1<SqlSpecificationRaw, List<Record>>() {
                    @Override
                    public List<Record> call(SqlSpecificationRaw sqlSpecificationRaw) {
                        return recordRepository.query(sqlSpecificationRaw);
                    }
                })
                .subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable addRecord(Record record) {
        return Observable.just(record)
                .map(new Func1<Record, Void>() {
                    @Override
                    public Void call(Record record) {
                        recordRepository.insert(Arrays.asList(record));
                        return null;
                    }
                })
                .subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }


    public Completable updateRecord(Record record) {
        ContentValues cv = recordMapper.toContentValues(record);
        SqlSpecificationWhere specificationWhere = new SqlSpecificationWhereByValue(RecordsTable.ID, Long.toString(record.getId()));
        SqlSpecificationUpdate sqlSpecificationUpdate = new SqlSpecificationUpdate(cv, specificationWhere);
        return Observable.just(sqlSpecificationUpdate)
                .map(new Func1<SqlSpecificationUpdate, Void>() {
                    @Override
                    public Void call(SqlSpecificationUpdate specificationUpdate) {
                        recordRepository.update(specificationUpdate);
                        return null;
                    }
                })
                .subscribeOn(dbScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }
}
