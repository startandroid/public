package ru.startandroid.vocabulary.data.exercise.storage;


import ru.startandroid.vocabulary.storage.database.ExercisesTable;
import ru.startandroid.vocabulary.storage.database.RecordsTable;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationRaw;

public class SqlSpecificationRawAllExercises implements SqlSpecificationRaw {
    @Override
    public String getQuery() {
        return String.format("select * from %s", ExercisesTable.TABLE_NAME);
    }

    @Override
    public String[] getSelectionArgs() {
        return new String[0];
    }
}
