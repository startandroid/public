package ru.startandroid.vocabulary.data.exercise;

import ru.startandroid.vocabulary.base.ItemController;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.storage.database.ExercisesTable;
import ru.startandroid.vocabulary.storage.database.ItemDatabaseRepository;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.RecordsTable;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhere;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhereByValue;
import rx.Scheduler;

public class ExerciseController extends ItemController<Exercise> {

    public ExerciseController(ItemDatabaseRepository<Exercise> itemRepository, Scheduler dbScheduler, ItemMapper<Exercise> itemMapper) {
        super(itemRepository, dbScheduler, itemMapper);
    }

    @Override
    protected SqlSpecificationWhere createSqlSpecificationWhereFromItem(Exercise item) {
        return new SqlSpecificationWhereByValue(ExercisesTable.ID, Long.toString(item.getId()));
    }
}
