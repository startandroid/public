package ru.startandroid.vocabulary.data.verb;

import ru.startandroid.vocabulary.base.ItemController;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.storage.database.ItemDatabaseRepository;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.RecordsTable;
import ru.startandroid.vocabulary.storage.database.VerbsTable;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhere;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhereByValue;
import rx.Scheduler;

public class VerbController extends ItemController<Verb> {

    public VerbController(ItemDatabaseRepository<Verb> itemRepository, Scheduler dbScheduler, ItemMapper<Verb> itemMapper) {
        super(itemRepository, dbScheduler, itemMapper);
    }

    @Override
    protected SqlSpecificationWhere createSqlSpecificationWhereFromItem(Verb item) {
        return new SqlSpecificationWhereByValue(VerbsTable.ID, Long.toString(item.getId()));
    }
}
