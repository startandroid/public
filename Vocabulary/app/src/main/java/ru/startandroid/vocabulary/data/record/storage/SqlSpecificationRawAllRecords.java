package ru.startandroid.vocabulary.data.record.storage;


import ru.startandroid.vocabulary.storage.database.RecordsTable;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationRaw;

public class SqlSpecificationRawAllRecords implements SqlSpecificationRaw {
    @Override
    public String getQuery() {
        return String.format("select * from %s", RecordsTable.TABLE_NAME);
    }

    @Override
    public String[] getSelectionArgs() {
        return new String[0];
    }
}
