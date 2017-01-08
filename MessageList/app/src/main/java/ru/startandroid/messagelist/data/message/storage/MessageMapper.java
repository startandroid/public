package ru.startandroid.messagelist.data.message.storage;

import android.content.ContentValues;
import android.database.Cursor;

import ru.startandroid.messagelist.data.message.Message;
import ru.startandroid.messagelist.storage.database.ItemMapper;
import ru.startandroid.messagelist.storage.database.MessagesTable;

public class MessageMapper implements ItemMapper<Message> {

    @Override
    public Message fromCursor(Cursor cursor) {
        Message message = new Message();
        message.setId(cursor.getString(cursor.getColumnIndex(MessagesTable.ID)));
        message.setTime(cursor.getLong(cursor.getColumnIndex(MessagesTable.TIME)));
        message.setText(cursor.getString(cursor.getColumnIndex(MessagesTable.TEXT)));
        message.setImage(cursor.getString(cursor.getColumnIndex(MessagesTable.IMAGE)));
        return message;
    }

    @Override
    public ContentValues toContentValues(Message item) {
        ContentValues cv = new ContentValues(4);
        cv.put(MessagesTable.ID, item.getId());
        cv.put(MessagesTable.TIME, item.getTime());
        cv.put(MessagesTable.TEXT, item.getText());
        cv.put(MessagesTable.IMAGE, item.getImage());
        return cv;
    }
}
