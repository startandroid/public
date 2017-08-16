package ru.startandroid.messagelist.data.database;

import java.util.Collection;
import java.util.List;

import io.reactivex.Observable;
import ru.startandroid.messagelist.data.database.specification.SqlSpecificationRaw;
import ru.startandroid.messagelist.data.database.specification.SqlSpecificationUpdate;

public interface ItemDatabaseDataSourceRx<I> {

    Observable<List<I>> query(SqlSpecificationRaw specification);

    Observable<List<Long>> insert(Collection<I> items);

    Observable<Integer> update(SqlSpecificationUpdate sqlSpecificationUpdate);

}
