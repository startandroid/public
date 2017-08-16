package ru.startandroid.messagelist.data.database;

public class MessagesTable {

    public static final String TABLE_NAME = "messages";

    public static final String ID = "_id";
    public static final String TIME = "datetime";
    public static final String TEXT = "message";
    public static final String IMAGE = "image";
    public static final String FAVORITE = "favorite";

    public static final String CREATE_SCRIPT = String.format(
            "create table %s (" +
                    "%s integer primary key, " +
                    "%s integer, " +
                    "%s text," +
                    "%s text, " +
                    "%s integer " +
                    ")",
            TABLE_NAME, ID, TIME, TEXT, IMAGE, FAVORITE);

}
