package ru.startandroid.messagelist.data.message;

import com.annimon.stream.Stream;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ru.startandroid.messagelist.domain.messages.DbMessage;


public class UiMessageMapper {

    @Inject
    public UiMessageMapper() {
    }

    public UiMessage mapToUiMessage(DbMessage message) {
        UiMessage uiMessage = new UiMessage();
        uiMessage.setId(message.getId());
        uiMessage.setTime(message.getTime());
        uiMessage.setText(message.getText());
        uiMessage.setImage(message.getImage());
        uiMessage.setFavorite(message.isFavorite());
        return uiMessage;
    }

    public List<UiMessage> mapToUiMessages(Collection<DbMessage> messages) {
        return Stream.of(messages)
                .map(this::mapToUiMessage)
                .toList();
    }

}
