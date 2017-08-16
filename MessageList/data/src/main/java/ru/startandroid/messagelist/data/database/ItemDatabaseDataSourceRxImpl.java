package ru.startandroid.messagelist.data.database;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import ru.startandroid.messagelist.data.database.specification.SqlSpecificationRaw;
import ru.startandroid.messagelist.data.database.specification.SqlSpecificationUpdate;

public class ItemDatabaseDataSourceRxImpl<I> implements ItemDatabaseDataSourceRx<I> {

    private final ItemDatabaseDataSource<I> itemDatabaseDataSource;

    public ItemDatabaseDataSourceRxImpl(ItemDatabaseDataSource<I> itemDatabaseDataSource) {
        this.itemDatabaseDataSource = itemDatabaseDataSource;
    }

    @Override
    public Observable<List<I>> query(SqlSpecificationRaw specification) {
        return Observable.fromCallable(new QueryCallable(specification));
    }

    class QueryCallable implements Callable<List<I>> {

        private final SqlSpecificationRaw specification;

        public QueryCallable(SqlSpecificationRaw specification) {
            this.specification = specification;
        }

        @Override
        public List<I> call() throws Exception {
            return itemDatabaseDataSource.query(specification);
        }
    }

    @Override
    public Observable<List<Long>> insert(Collection<I> items) {
        return Observable.fromCallable(new InsertCallable(items));
    }

    class InsertCallable implements Callable<List<Long>> {

        private final Collection<I> items;

        public InsertCallable(Collection<I> items) {
            this.items = items;
        }

        @Override
        public List<Long> call() throws Exception {
            return itemDatabaseDataSource.insert(items);
        }
    }

    @Override
    public Observable<Integer> update(SqlSpecificationUpdate sqlSpecificationUpdate) {
        return Observable.fromCallable(new UpdateCallable(sqlSpecificationUpdate));
    }

    class UpdateCallable implements Callable<Integer> {

        private final SqlSpecificationUpdate sqlSpecificationUpdate;

        public UpdateCallable(SqlSpecificationUpdate sqlSpecificationUpdate) {
            this.sqlSpecificationUpdate = sqlSpecificationUpdate;
        }

        @Override
        public Integer call() throws Exception {
            return itemDatabaseDataSource.update(sqlSpecificationUpdate);
        }
    }
}
