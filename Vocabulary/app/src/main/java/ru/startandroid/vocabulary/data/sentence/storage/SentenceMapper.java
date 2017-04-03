package ru.startandroid.vocabulary.data.sentence.storage;

import android.content.ContentValues;
import android.database.Cursor;

import ru.startandroid.vocabulary.data.sentence.Sentence;
import ru.startandroid.vocabulary.storage.database.ExercisesTable;
import ru.startandroid.vocabulary.storage.database.ItemMapper;
import ru.startandroid.vocabulary.storage.database.SentencesTable;

public class SentenceMapper implements ItemMapper<Sentence> {
    @Override
    public Sentence fromCursor(Cursor cursor) {
        Sentence item = new Sentence();
        item.setId(cursor.getLong(cursor.getColumnIndex(SentencesTable.ID)));
        item.setRussian(cursor.getString(cursor.getColumnIndex(SentencesTable.RUSSIAN)));
        item.setEnglish(cursor.getString(cursor.getColumnIndex(SentencesTable.ENGLISH)));
        return item;
    }

    @Override
    public ContentValues toContentValues(Sentence item) {
        ContentValues cv = new ContentValues(2);
        cv.put(SentencesTable.RUSSIAN, item.getRussian());
        cv.put(SentencesTable.ENGLISH, item.getEnglish());
        return cv;
    }
}
