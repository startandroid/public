package ru.startandroid.vocabulary.storage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 1;

    Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(RecordsTable.CREATE_SCRIPT);
            db.execSQL(VerbsTable.CREATE_SCRIPT);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        fillVerbsData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing
    }

    private void fillVerbsData(SQLiteDatabase db) {
        BufferedReader bufferedReader = null;
        db.beginTransaction();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(mContext.getAssets().open("verbs.txt")));
            String line;
            ContentValues cv = new ContentValues(4);
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(";");
                if (strings.length >= 4) {
                    cv.clear();;
                    cv.put(VerbsTable.FIRST, strings[0]);
                    cv.put(VerbsTable.SECOND, strings[1]);
                    cv.put(VerbsTable.THIRD, strings[2]);
                    cv.put(VerbsTable.TRANSLATE, strings[3]);
                    db.insert(VerbsTable.TABLE_NAME, null, cv);
                }
            }
            db.setTransactionSuccessful();
        } catch (IOException ioException) {

        } finally {
            if (bufferedReader == null) {
                try {
                    bufferedReader.close();
                } catch (IOException ioException) {

                }
            }
            db.endTransaction();
        }
    }
}
