package ru.startandroid.vocabulary.data.exercise.storage;

import android.content.ContentValues;
import android.database.Cursor;

import ru.startandroid.vocabulary.data.exercise.Exercise;
import ru.startandroid.vocabulary.storage.database.ExercisesTable;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.RecordsTable;

public class ExerciseMapper implements ItemMapper<Exercise> {

    @Override
    public Exercise fromCursor(Cursor cursor) {
        Exercise item = new Exercise();
        item.setId(cursor.getLong(cursor.getColumnIndex(RecordsTable.ID)));
        item.setName(cursor.getString(cursor.getColumnIndex(ExercisesTable.NAME)));
        item.setEnabled(cursor.getInt(cursor.getColumnIndex(ExercisesTable.ENABLED)) == 1);
        return item;
    }

    @Override
    public ContentValues toContentValues(Exercise item) {
        ContentValues cv = new ContentValues(2);
        cv.put(ExercisesTable.NAME, item.getName());
        cv.put(ExercisesTable.ENABLED, item.isEnabled() ? 1 : 0);
        return cv;
    }
}
