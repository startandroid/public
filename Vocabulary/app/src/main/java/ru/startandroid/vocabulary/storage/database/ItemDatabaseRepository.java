package ru.startandroid.vocabulary.storage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.startandroid.vocabulary.BuildConfig;
import ru.startandroid.vocabulary.events.Event;
import ru.startandroid.vocabulary.events.EventBus;
import ru.startandroid.vocabulary.events.Events;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationRaw;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationUpdate;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationWhere;


public class ItemDatabaseRepository<I> {

    private static final long DELAY = 0;

    private final SQLiteOpenHelper sqLiteOpenHelper;
    private final ItemMapper<I> itemMapper;
    private final String tableName;
    private final EventBus eventBus;
    private final Event itemUpdatedEvent;

    public ItemDatabaseRepository(SQLiteOpenHelper sqLiteOpenHelper, ItemMapper<I> itemMapper, String tableName, EventBus eventBus) {
        this(sqLiteOpenHelper, itemMapper, tableName, eventBus, Events.EMPTY);
    }

    public ItemDatabaseRepository(SQLiteOpenHelper sqLiteOpenHelper, ItemMapper<I> itemMapper, String tableName, EventBus eventBus, Event itemUpdatedEvent) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
        this.itemMapper = itemMapper;
        this.tableName = tableName;
        this.eventBus = eventBus;
        this.itemUpdatedEvent = itemUpdatedEvent;
    }

    public List<I> query(SqlSpecificationRaw specification) {
        final List<I> items = new ArrayList<>();
        SQLiteDatabase database = getDatabase();
        String query = specification.getQuery();

        Cursor cursor = database.rawQuery(query, specification.getSelectionArgs());
        for (int i = 0, size = cursor.getCount(); i < size; i++) {
            cursor.moveToPosition(i);
            items.add(cursorToItem(cursor));
        }
        cursor.close();
        return items;
    }

    public List<Long> insert(Collection<I> items) {
        List<Long> ids = new ArrayList<>(items.size());

        SQLiteDatabase database = getDatabase();
        boolean success = false;
        database.beginTransaction();
        try {
            for (I item : items) {
                ContentValues contentValues = itemToCv(item);
                ids.add(database.insert(tableName, null, contentValues));
            }

            database.setTransactionSuccessful();
            success = true;
        } finally {
            database.endTransaction();
        }
        if (success) {
            eventBus.postEvent(itemUpdatedEvent);
        }
        return ids;
    }

    public int update(SqlSpecificationUpdate sqlSpecificationUpdate)  {
        SQLiteDatabase db = getDatabase();
        int upd = db.update(tableName, sqlSpecificationUpdate.getContentValues(), sqlSpecificationUpdate.getWhereClause(), sqlSpecificationUpdate.getWhereArgs());
        eventBus.postEvent(itemUpdatedEvent);
        return upd;
    }

    public int delete(SqlSpecificationWhere specification)  {
        SQLiteDatabase db = getDatabase();
        int del = db.delete(tableName, specification.getWhereClause(), specification.getWhereArgs());
        eventBus.postEvent(itemUpdatedEvent);
        return del;
    }


    private SQLiteDatabase getDatabase() {
        return sqLiteOpenHelper.getWritableDatabase();
    }

    private I cursorToItem(Cursor cursor) {
        return itemMapper.fromCursor(cursor);
    }

    private ContentValues itemToCv(I item) {
        return itemMapper.toContentValues(item);
    }

    private void delay() {
        if (BuildConfig.DEBUG && DELAY > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
