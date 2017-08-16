package ru.startandroid.messagelist.data.database.specification;

public interface SqlSpecificationRaw {
    String getQuery();
    String[] getSelectionArgs();
}
