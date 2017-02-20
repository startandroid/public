package ru.startandroid.vocabulary.data.record.storage;

import android.content.ContentValues;
import android.database.Cursor;

import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.RecordsTable;

public class RecordMapper implements ItemMapper<Record> {

    @Override
    public Record fromCursor(Cursor cursor) {
        Record message = new Record();
        message.setId(cursor.getLong(cursor.getColumnIndex(RecordsTable.ID)));
        message.setWord(cursor.getString(cursor.getColumnIndex(RecordsTable.WORD)));
        message.setTranslate(cursor.getString(cursor.getColumnIndex(RecordsTable.TRANSLATE)));
        message.setSample(cursor.getString(cursor.getColumnIndex(RecordsTable.SAMPLE)));
        message.setDefinition(cursor.getString(cursor.getColumnIndex(RecordsTable.DEFINITION)));
        message.setRememberedCount(cursor.getLong(cursor.getColumnIndex(RecordsTable.REMEMBERED_COUNT)));
        message.setAdded(cursor.getLong(cursor.getColumnIndex(RecordsTable.ADDED)));
        return message;
    }

    @Override
    public ContentValues toContentValues(Record item) {
        ContentValues cv = new ContentValues(5);
        cv.put(RecordsTable.WORD, item.getWord());
        cv.put(RecordsTable.TRANSLATE, item.getTranslate());
        cv.put(RecordsTable.SAMPLE, item.getSample());
        cv.put(RecordsTable.DEFINITION, item.getDefinition());
        cv.put(RecordsTable.REMEMBERED_COUNT, item.getRememberedCount());
        cv.put(RecordsTable.ADDED, item.getAdded());
        return cv;
    }
}
