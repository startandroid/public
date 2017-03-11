package ru.startandroid.vocabulary.storage.database;

public class VerbsTable {

    public static final String TABLE_NAME = "verbs";

    public static final String ID = "_id";
    public static final String FIRST = "first";
    public static final String SECOND = "second";
    public static final String THIRD = "third";
    public static final String TRANSLATE = "translate";
    public static final String REMEMBERED_COUNT = "remembered_count";
    public static final String SHOW_AFTER = "show_after";

    public static final String CREATE_SCRIPT = String.format(
            "create table %s (" +
                    "%s integer primary key autoincrement, " +
                    "%s text, " +
                    "%s text," +
                    "%s text, " +
                    "%s text, " +
                    "%s integer, " +
                    "%s integer " +
                    ")",
            TABLE_NAME, ID, FIRST, SECOND, THIRD, TRANSLATE, REMEMBERED_COUNT, SHOW_AFTER);
}
