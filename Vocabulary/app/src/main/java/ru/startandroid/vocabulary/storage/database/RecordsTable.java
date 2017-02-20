package ru.startandroid.vocabulary.storage.database;

public class RecordsTable {

    public static final String TABLE_NAME = "records";

    public static final String ID = "_id";
    public static final String WORD = "word";
    public static final String TRANSLATE = "translate";
    public static final String SAMPLE = "sample";
    public static final String DEFINITION = "definition";
    public static final String REMEMBERED_COUNT = "remembered_count";
    public static final String ADDED = "added";
    public static final String SHOW_AFTER = "show_after";

    public static final String CREATE_SCRIPT = String.format(
            "create table %s (" +
                    "%s integer primary key autoincrement, " +
                    "%s text, " +
                    "%s text," +
                    "%s text, " +
                    "%s text, " +
                    "%s integer, " +
                    "%s integer, " +
                    "%s integer " +
                    ")",
            TABLE_NAME, ID, WORD, TRANSLATE, SAMPLE, DEFINITION, REMEMBERED_COUNT, ADDED, SHOW_AFTER);
}
