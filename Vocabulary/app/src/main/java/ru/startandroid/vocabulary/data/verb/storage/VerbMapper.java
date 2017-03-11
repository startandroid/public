package ru.startandroid.vocabulary.data.verb.storage;

import android.content.ContentValues;
import android.database.Cursor;

import ru.startandroid.vocabulary.data.record.Record;
import ru.startandroid.vocabulary.data.verb.Verb;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.RecordsTable;
import ru.startandroid.vocabulary.storage.database.VerbsTable;

public class VerbMapper implements ItemMapper<Verb> {

    @Override
    public Verb fromCursor(Cursor cursor) {
        Verb item = new Verb();
        item.setId(cursor.getLong(cursor.getColumnIndex(VerbsTable.ID)));
        item.setFirst(cursor.getString(cursor.getColumnIndex(VerbsTable.FIRST)));
        item.setSecond(cursor.getString(cursor.getColumnIndex(VerbsTable.SECOND)));
        item.setThird(cursor.getString(cursor.getColumnIndex(VerbsTable.THIRD)));
        item.setTranslate(cursor.getString(cursor.getColumnIndex(VerbsTable.TRANSLATE)));
        item.setRememberedCount(cursor.getLong(cursor.getColumnIndex(VerbsTable.REMEMBERED_COUNT)));
        return item;
    }

    @Override
    public ContentValues toContentValues(Verb item) {
        ContentValues cv = new ContentValues(5);
        cv.put(VerbsTable.FIRST, item.getFirst());
        cv.put(VerbsTable.SECOND, item.getSecond());
        cv.put(VerbsTable.THIRD, item.getThird());
        cv.put(VerbsTable.TRANSLATE, item.getTranslate());
        cv.put(VerbsTable.REMEMBERED_COUNT, item.getRememberedCount());
        return cv;
    }
}
