package ru.startandroid.messagelist.data.messages;

import java.util.List;

import io.reactivex.Observable;

public interface MessageDataSourceRemote {
    Observable<List<ApiMessage>> getMessages(int page);
}
