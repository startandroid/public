package ru.startandroid.vocabulary.data.record.storage;


import ru.startandroid.vocabulary.storage.database.RecordsTable;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationRaw;

public class SqlSpecificationRawAllEnabledRecords implements SqlSpecificationRaw {
    @Override
    public String getQuery() {
        return String.format("select * from %s where %s = ?", RecordsTable.TABLE_NAME, RecordsTable.ENABLED);
    }

    @Override
    public String[] getSelectionArgs() {
        return new String[]{"1"};
    }
}
