package ru.startandroid.vocabulary.storage.database.specification;

public interface SqlSpecificationRaw {
    String getQuery();
    String[] getSelectionArgs();
}
