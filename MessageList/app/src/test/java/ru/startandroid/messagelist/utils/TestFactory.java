package ru.startandroid.messagelist.utils;

import java.util.ArrayList;
import java.util.List;

import ru.startandroid.messagelist.data.message.UiMessage;
import ru.startandroid.messagelist.domain.messages.DbMessage;

public class TestFactory {

    public static final int COUNT = 5;

    public static final String MESSAGE = "message";
    public static final int TIME = 1000;
    public static final boolean FAVORITE = false;

    public static List<UiMessage> createFakeUiMessages() {
        List list = new ArrayList(COUNT);
        UiMessage message;
        for (int i = 1; i < COUNT; i++) {
            message = new UiMessage();
            message.setId(i);
            message.setText(MESSAGE + i);
            //message.setImage("Image " + i);
            message.setTime(TIME * i);
            message.setFavorite(FAVORITE);
            list.add(message);
        }
        return list;
    }

    public static List<DbMessage> createFakeDbMessages() {
        List list = new ArrayList(COUNT);
        DbMessage message;
        for (int i = 1; i < COUNT; i++) {
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
