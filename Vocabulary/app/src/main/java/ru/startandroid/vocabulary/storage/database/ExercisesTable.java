package ru.startandroid.vocabulary.storage.database;

public class ExercisesTable {

    public static final String TABLE_NAME = "exercises";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String ENABLED = "enabled"; // 0 - disable, 1 - enable

    public static final String CREATE_SCRIPT = String.format(
            "create table %s (" +
                    "%s integer primary key, " +
                    "%s text, " +
                    "%s text," +
                    "%s integer " +
                    ")",
            TABLE_NAME, ID, NAME, DESCRIPTION, ENABLED);
}
