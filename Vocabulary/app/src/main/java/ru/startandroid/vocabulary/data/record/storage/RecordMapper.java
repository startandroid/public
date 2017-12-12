package ru.startandroid.vocabulary.data.record.storage;

import android.content.ContentValues;
import android.database.Cursor;

import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.RecordsTable;

public class RecordMapper implements ItemMapper<Record> {

    @Override
    public Record fromCursor(Cursor cursor) {
        Record record = new Record();
        record.setId(cursor.getLong(cursor.getColumnIndex(RecordsTable.ID)));
        record.setWord(cursor.getString(cursor.getColumnIndex(RecordsTable.WORD)));
        record.setTranslate(cursor.getString(cursor.getColumnIndex(RecordsTable.TRANSLATE)));
        record.setSample(cursor.getString(cursor.getColumnIndex(RecordsTable.SAMPLE)));
        record.setDefinition(cursor.getString(cursor.getColumnIndex(RecordsTable.DEFINITION)));
        record.setRememberedCount(cursor.getLong(cursor.getColumnIndex(RecordsTable.REMEMBERED_COUNT)));
        record.setEnabled(cursor.getLong(cursor.getColumnIndex(RecordsTable.ENABLED)) == 1);
        record.setAdded(cursor.getLong(cursor.getColumnIndex(RecordsTable.ADDED)));
        return record;
    }

    @Override
    public ContentValues toContentValues(Record item) {
        ContentValues cv = new ContentValues(6);
        cv.put(RecordsTable.WORD, item.getWord());
        cv.put(RecordsTable.TRANSLATE, item.getTranslate());
        cv.put(RecordsTable.SAMPLE, item.getSample());
        cv.put(RecordsTable.DEFINITION, item.getDefinition());
        cv.put(RecordsTable.REMEMBERED_COUNT, item.getRememberedCount());
        cv.put(RecordsTable.ENABLED, item.isEnabled() ? 1 : 0);
        cv.put(RecordsTable.ADDED, item.getAdded());
        return cv;
    }
}
