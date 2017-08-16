package ru.startandroid.messagelist.data.messages;

import java.util.List;

import io.reactivex.Observable;
import ru.startandroid.messagelist.data.webapi.MessagesApi;

public class MessageDataSourceRemoteImpl implements MessageDataSourceRemote {

    private final MessagesApi messagesApi;

    public MessageDataSourceRemoteImpl(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    @Override
    public Observable<List<ApiMessage>> getMessages(int page) {
        return messagesApi.messages(page);
    }
}
