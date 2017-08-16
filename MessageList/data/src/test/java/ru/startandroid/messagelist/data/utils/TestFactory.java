package ru.startandroid.messagelist.data.utils;

import java.util.ArrayList;
import java.util.List;

import ru.startandroid.messagelist.data.messages.ApiMessage;
import ru.startandroid.messagelist.domain.messages.DbMessage;

public class TestFactory {

    public static final String MESSAGE = "message";
    public static final int TIME = 1000;
    public static final boolean FAVORITE = false;

    public static final int COUNT = 5;


    public static List<ApiMessage> createFakeApiMessages() {
        return createFakeApiMessages(COUNT);
    }

    public static List<ApiMessage> createFakeApiMessages(int count) {
        List list = new ArrayList(count);
        ApiMessage message;
        for (int i = 1; i <= count; i++) {
            message = new ApiMessage();
            message.setId(i);
            message.setText(MESSAGE + i);
            message.setTime(TIME * i);
            list.add(message);
        }
        return list;
    }

    public static List<DbMessage> createFakeDbMessages() {
        return createFakeDbMessages(COUNT);
    }

    public static List<DbMessage> createFakeDbMessages(int count) {
        return createFakeDbMessages(1, count);
    }

    public static List<DbMessage> createFakeDbMessages(int start, int count) {
        List list = new ArrayList(count);
        DbMessage message;
        for (int i = start; i < start + count; i++) {
            message = new DbMessage();
            message.setId(i);
            message.setText(MESSAGE + i);
            message.setTime(TIME * i);
            message.setFavorite(FAVORITE);
            list.add(message);
        }
        return list;
    }
}
