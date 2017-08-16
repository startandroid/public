package ru.startandroid.messagelist.domain.usecase.message;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.startandroid.messagelist.domain.messages.DbMessage;
import ru.startandroid.messagelist.domain.messages.MessageRepository;
import ru.startandroid.messagelist.domain.usecase.base.ObservableUseCaseWithParams;

public class GetMessagesUseCase extends ObservableUseCaseWithParams<Integer, List<DbMessage>> {

    private final MessageRepository messageRepository;

    @Inject
    public GetMessagesUseCase(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    protected Observable<List<DbMessage>> createObservable(Integer page) {
        return messageRepository.getMessages(page);
    }
}
