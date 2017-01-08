package ru.startandroid.messagelist.storage.database.specification;

public interface SqlSpecificationRaw {
    String getQuery();
    String[] getSelectionArgs();
}
