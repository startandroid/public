package ru.startandroid.messagelist.data.database;

import java.util.Collection;
import java.util.List;

import ru.startandroid.messagelist.data.database.specification.SqlSpecificationRaw;
import ru.startandroid.messagelist.data.database.specification.SqlSpecificationUpdate;

public interface ItemDatabaseDataSource<I> {

    List<I> query(SqlSpecificationRaw specification);

    List<Long> insert(Collection<I> items);

    int update(SqlSpecificationUpdate sqlSpecificationUpdate);

}
