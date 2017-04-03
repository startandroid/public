package ru.startandroid.vocabulary.storage.database;

public class SentencesTable {

    public static final String TABLE_NAME = "sentences";

    public static final String ID = "_id";
    public static final String SENTENCE_ID = "sentence_id";
    public static final String EXERCISE_ID = "exercise_id";
    public static final String RUSSIAN = "russian";
    public static final String ENGLISH = "english";
    public static final String REMEMBERED_COUNT = "remembered_count";
    public static final String SHOW_AFTER = "show_after";

    public static final String CREATE_SCRIPT = String.format(
            "create table %s (" +
                    "%s integer primary key autoincrement, " +
                    "%s integer, " +
                    "%s integer," +
                    "%s text, " +
                    "%s text, " +
                    "%s integer, " +
                    "%s integer " +
                    ")",
            TABLE_NAME, ID, SENTENCE_ID, EXERCISE_ID, RUSSIAN, ENGLISH, REMEMBERED_COUNT, SHOW_AFTER);
}
