package ru.startandroid.vocabulary.data.verb.storage;


import ru.startandroid.vocabulary.storage.database.RecordsTable;
import ru.startandroid.vocabulary.storage.database.VerbsTable;
import ru.startandroid.vocabulary.storage.database.specification.SqlSpecificationRaw;

public class SqlSpecificationRawAllVerbs implements SqlSpecificationRaw {
    @Override
    public String getQuery() {
        return String.format("select * from %s", VerbsTable.TABLE_NAME);
    }

    @Override
    public String[] getSelectionArgs() {
        return new String[0];
    }
}
