package ru.startandroid.messagelist.data.messages;

import android.content.ContentValues;
import android.database.Cursor;

import com.annimon.stream.Stream;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ru.startandroid.messagelist.data.common.Constants;
import ru.startandroid.messagelist.data.database.ItemMapper;
import ru.startandroid.messagelist.data.database.MessagesTable;
import ru.startandroid.messagelist.domain.messages.DbMessage;


public class DbMessageMapper implements ItemMapper<DbMessage> {

    @Inject
    public DbMessageMapper() {
    }

    @Override
    public DbMessage fromCursor(Cursor cursor) {
        DbMessage message = new DbMessage();
        message.setId(cursor.getLong(cursor.getColumnIndex(MessagesTable.ID)));
        message.setTime(cursor.getLong(cursor.getColumnIndex(MessagesTable.TIME)));
        message.setText(cursor.getString(cursor.getColumnIndex(MessagesTable.TEXT)));
        message.setImage(cursor.getString(cursor.getColumnIndex(MessagesTable.IMAGE)));
        message.setFavorite(cursor.getInt(cursor.getColumnIndex(MessagesTable.FAVORITE)) == Constants.DB_TRUE);
        return message;
    }

    @Override
    public ContentValues toContentValues(DbMessage item) {
        ContentValues cv = new ContentValues(4);
        cv.put(MessagesTable.ID, item.getId());
        cv.put(MessagesTable.TIME, item.getTime());
        cv.put(MessagesTable.TEXT, item.getText());
        cv.put(MessagesTable.IMAGE, item.getImage());
        putFavorite(cv, item.isFavorite());
        return cv;
    }

    public void putFavorite(ContentValues cv, boolean favorite) {
        cv.put(MessagesTable.FAVORITE, favorite ? Constants.DB_TRUE : Constants.DB_FALSE);
    }

    public DbMessage mapToDbMessage(ApiMessage apiMessage) {
        DbMessage message = new DbMessage();
        message.setId(apiMessage.getId());
        message.setTime(apiMessage.getTime());
        message.setText(apiMessage.getText());
        message.setImage(apiMessage.getImage());
        message.setFavorite(false);
        return message;
    }

    public List<DbMessage> mapToDbMessages(Collection<ApiMessage> messages) {
        return Stream.of(messages)
                .map(this::mapToDbMessage)
                .toList();
    }

}