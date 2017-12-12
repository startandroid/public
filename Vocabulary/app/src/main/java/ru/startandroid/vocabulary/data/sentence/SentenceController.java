package ru.startandroid.vocabulary.data.sentence;

import ru.startandroid.vocabulary.base.ItemController;
import ru.startandroid.vocabulary.storage.database.ItemDatabaseRepository;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.SentencesTable;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhere;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhereByValue;
import rx.Scheduler;

public class SentenceController extends ItemController<Sentence> {

    public SentenceController(ItemDatabaseRepository<Sentence> itemRepository, Scheduler dbScheduler, ItemMapper<Sentence> itemMapper) {
        super(itemRepository, dbScheduler, itemMapper);
    }

    @Override
    protected SqlSpecificationWhere createSqlSpecificationWhereFromItem(Sentence item) {
        return new SqlSpecificationWhereByValue(SentencesTable.ID, Long.toString(item.getExerciseId()));
    }
}
