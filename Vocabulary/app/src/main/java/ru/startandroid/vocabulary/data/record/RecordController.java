package ru.startandroid.vocabulary.data.record;

import ru.startandroid.vocabulary.base.ItemController;
import ru.startandroid.vocabulary.storage.database.ItemDatabaseRepository;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.RecordsTable;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhere;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhereByValue;
import rx.Scheduler;

public class RecordController extends ItemController<Record> {

    public RecordController(ItemDatabaseRepository<Record> itemRepository, Scheduler dbScheduler, ItemMapper<Record> itemMapper) {
        super(itemRepository, dbScheduler, itemMapper);
    }

    @Override
    protected SqlSpecificationWhere createSqlSpecificationWhereFromItem(Record item) {
        return new SqlSpecificationWhereByValue(RecordsTable.ID, Long.toString(item.getId()));
    }
}
