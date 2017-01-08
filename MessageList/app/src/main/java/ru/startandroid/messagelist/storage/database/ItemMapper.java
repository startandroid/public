package ru.startandroid.messagelist.storage.database;

import android.content.ContentValues;
import android.database.Cursor;

public interface ItemMapper<I> {
    I fromCursor(Cursor cursor);
    ContentValues toContentValues(I item);
}
