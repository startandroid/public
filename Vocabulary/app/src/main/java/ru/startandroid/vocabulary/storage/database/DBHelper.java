package ru.startandroid.vocabulary.storage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 3;

    Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
        Log.d("qweee", "DBHelper create");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(RecordsTable.CREATE_SCRIPT);
            db.execSQL(VerbsTable.CREATE_SCRIPT);
            db.execSQL(ExercisesTable.CREATE_SCRIPT);
            db.execSQL(SentencesTable.CREATE_SCRIPT);
            fillVerbsData(db);
            fillExercisesAndSentencesData(db);
            //fillRecordsData(db);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        fillVerbsData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        Log.d("qweee", "onUpgrade " + oldVersion + ", " + newVersion);
//        db.beginTransaction();
//        try {
//            update_2(db, oldVersion);
//            update_3(db, oldVersion);
//            db.setTransactionSuccessful();
//        } finally {
//            db.endTransaction();
//        }


    }
//
//    private void update_2(SQLiteDatabase db, int oldVersion) {
//        if (oldVersion >= 2) {
//            return;
//        }
//        db.execSQL(VerbsTable.CREATE_SCRIPT);
//        fillVerbsData(db);
//
//    }
//
//    private void update_3(SQLiteDatabase db, int oldVersion) {
//        if (oldVersion >= 3) {
//            return;
//        }
//        db.execSQL(ExercisesTable.CREATE_SCRIPT);
//        db.execSQL(SentencesTable.CREATE_SCRIPT);
//        fillExercisesAndSentencesData(db);
//    }


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
                    cv.clear();
                    cv.put(VerbsTable.FIRST, strings[0]);
                    cv.put(VerbsTable.SECOND, strings[1]);
                    cv.put(VerbsTable.THIRD, strings[2]);
                    cv.put(VerbsTable.TRANSLATE, strings[3]);
                    db.insert(VerbsTable.TABLE_NAME, null, cv);
                }
            }
            db.setTransactionSuccessful();
        } catch (IOException ioException) {
            ioException.printStackTrace();
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


    private void fillExercisesAndSentencesData(SQLiteDatabase db) {
        Log.d("qweee", "fillExercisesAndSentencesData");
        BufferedReader bufferedReader = null;
        db.beginTransaction();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(mContext.getAssets().open("sentences.txt")));
            String line;
            int exerciseId = 0;
            ContentValues cv = new ContentValues(4);
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split("###");
                if (strings.length >= 4) {
                    if (exerciseId != Integer.parseInt(strings[0])) {
                        cv.clear();
                        cv.put(ExercisesTable.ID, strings[0]);
                        cv.put(ExercisesTable.NAME, "Exercise " + strings[0]);
                        cv.put(ExercisesTable.ENABLED, 0);
                        db.insert(ExercisesTable.TABLE_NAME, null, cv);
                        exerciseId = Integer.parseInt(strings[0]);
                    }

                    cv.clear();
                    cv.put(SentencesTable.EXERCISE_ID, strings[0]);
                    cv.put(SentencesTable.SENTENCE_ID, strings[1]);
                    cv.put(SentencesTable.RUSSIAN, strings[2]);
                    cv.put(SentencesTable.ENGLISH, strings[3]);
                    db.insert(SentencesTable.TABLE_NAME, null, cv);
                }
            }
            db.setTransactionSuccessful();
            Log.d("qweee", "fillExercisesAndSentencesData setTransactionSuccessful");
        } catch (IOException ioException) {
            Log.e("qweee", "fillExercisesAndSentencesData", ioException);
            ioException.printStackTrace();
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


    private void fillRecordsData(SQLiteDatabase db) {
        BufferedReader bufferedReader = null;
        db.beginTransaction();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(mContext.getAssets().open("records.txt")));
            String line;
            ContentValues cv = new ContentValues(4);
            while ((line = bufferedReader.readLine()) != null) {
                cv.clear();
                String[] strings = line.split("#");
                if (strings.length >= 2) {
                    cv.put(RecordsTable.WORD, strings[0]);
                    cv.put(RecordsTable.TRANSLATE, strings[1]);
                }
                if (strings.length >= 3) {
                    cv.put(RecordsTable.SAMPLE, strings[2]);
                }
                if (strings.length == 4) {
                    cv.put(RecordsTable.DEFINITION, strings[3]);
                }


                db.insert(RecordsTable.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } catch (IOException ioException) {
            ioException.printStackTrace();
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
