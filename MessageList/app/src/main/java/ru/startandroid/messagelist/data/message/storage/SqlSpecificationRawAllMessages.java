package ru.startandroid.messagelist.data.message.storage;

import ru.startandroid.messagelist.storage.database.MessagesTable;
import ru.startandroid.messagelist.storage.database.specification.SqlSpecificationRaw;

public class SqlSpecificationRawAllMessages implements SqlSpecificationRaw {
    @Override
    public String getQuery() {
        return String.format("select * from %s", MessagesTable.TABLE_NAME);
    }

    @Override
    public String[] getSelectionArgs() {
        return new String[0];
    }
}
