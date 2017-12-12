package ru.startandroid.vocabulary.data.exercise;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.startandroid.vocabulary.base.ItemController;
import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.storage.database.ExercisesTable;
import ru.startandroid.vocabulary.storage.database.ItemDatabaseRepository;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.RecordsTable;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationUpdate;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhere;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhereByValue;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhereByValues;
import rx.Completable;
import rx.Scheduler;

public class ExerciseController extends ItemController<Exercise> {

    public ExerciseController(ItemDatabaseRepository<Exercise> itemRepository, Scheduler dbScheduler, ItemMapper<Exercise> itemMapper) {
        super(itemRepository, dbScheduler, itemMapper);
    }

    @Override
    protected SqlSpecificationWhere createSqlSpecificationWhereFromItem(Exercise item) {
        return new SqlSpecificationWhereByValue(ExercisesTable.ID, Long.toString(item.getId()));
    }

    public Completable selectExercises(List<Long> idList) {
        ContentValues cv = new ContentValues(1);
        cv.put(ExercisesTable.ENABLED, 0);
        SqlSpecificationUpdate sqlSpecificationUpdateAllDisabled = new SqlSpecificationUpdate(cv, null);

        cv = new ContentValues(1);
        cv.put(ExercisesTable.ENABLED, 1);
        String[] values = new String[idList.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = Long.toString(idList.get(i));
        }
        SqlSpecificationWhere specificationWhere = new SqlSpecificationWhereByValues(ExercisesTable.ID, values);
        SqlSpecificationUpdate sqlSpecificationUpdateAllEnabled = new SqlSpecificationUpdate(cv, specificationWhere);

        List<SqlSpecificationUpdate> list = new ArrayList<>(2);
        Collections.addAll(list, sqlSpecificationUpdateAllDisabled, sqlSpecificationUpdateAllEnabled);
        return update(list);
    }
}
