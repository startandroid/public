package ru.startandroid.messagelist.data.messages;

import android.content.ContentValues;

import javax.inject.Inject;

import ru.startandroid.messagelist.data.common.Constants;
import ru.startandroid.messagelist.data.database.MessagesTable;
import ru.startandroid.messagelist.data.database.specification.SqlSpecificationRaw;
import ru.startandroid.messagelist.data.database.specification.SqlSpecificationUpdate;
import ru.startandroid.messagelist.data.database.specification.SqlSpecificationWhere;
import ru.startandroid.messagelist.data.database.specification.SqlSpecificationWhereByValue;


public class SqlSpecificationMessageFactory {

    private final DbMessageMapper dbMessageMapper;

    @Inject
    public SqlSpecificationMessageFactory(DbMessageMapper dbMessageMapper) {
        this.dbMessageMapper = dbMessageMapper;
    }

    public SqlSpecificationRaw getMessages(final int page) {
        return new SqlSpecificationRaw() {
            @Override
            public String getQuery() {
                return String.format("select * from %s limit ? offset ?", MessagesTable.TABLE_NAME);
            }

            @Override
            public String[] getSelectionArgs() {
                return new String[]{String.valueOf(Constants.PAGE_SIZE), String.valueOf(Constants.PAGE_SIZE * (page -1))};
            }
        };
    }

    public SqlSpecificationUpdate updateMessageFavorite(long id, boolean favorite) {
        ContentValues cv = new ContentValues(1);
        dbMessageMapper.putFavorite(cv, favorite);
        SqlSpecificationWhere where = new SqlSpecificationWhereByValue(MessagesTable.ID, String.valueOf(id));
        return new SqlSpecificationUpdate(cv, where);
    }
}
