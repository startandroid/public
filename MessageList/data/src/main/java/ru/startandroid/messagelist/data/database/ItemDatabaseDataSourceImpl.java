package ru.startandroid.messagelist.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.startandroid.messagelist.data.database.specification.SqlSpecificationRaw;
import ru.startandroid.messagelist.data.database.specification.SqlSpecificationUpdate;

public class ItemDatabaseDataSourceImpl<I> implements ItemDatabaseDataSource<I> {

    private final SQLiteOpenHelper sqLiteOpenHelper;
    private final ItemMapper<I> itemMapper;
    private final String tableName;

    public ItemDatabaseDataSourceImpl(SQLiteOpenHelper sqLiteOpenHelper, ItemMapper<I> itemMapper, String tableName) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
        this.itemMapper = itemMapper;
        this.tableName = tableName;
    }

    @Override
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

    @Override
    public List<Long> insert(Collection<I> items) {
        List<Long> ids = new ArrayList<>(items.size());

        SQLiteDatabase database = getDatabase();
        database.beginTransaction();
        try {
            for (I item : items) {
                ContentValues contentValues = itemToCv(item);
                ids.add(database.insert(tableName, null, contentValues));
            }

            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        return ids;
    }

    @Override
    public int update(SqlSpecificationUpdate sqlSpecificationUpdate)  {
        SQLiteDatabase db = getDatabase();
        return db.update(tableName, sqlSpecificationUpdate.getContentValues(), sqlSpecificationUpdate.getWhereClause(), sqlSpecificationUpdate.getWhereArgs());
    }

    SQLiteDatabase getDatabase() {
        return sqLiteOpenHelper.getWritableDatabase();
    }

    private I cursorToItem(Cursor cursor) {
        return itemMapper.fromCursor(cursor);
    }

    private ContentValues itemToCv(I item) {
        return itemMapper.toContentValues(item);
    }

}
