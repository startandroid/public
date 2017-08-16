package ru.startandroid.messagelist.domain.usecase.message;

import javax.inject.Inject;

import io.reactivex.Completable;
import ru.startandroid.messagelist.domain.common.Pair;
import ru.startandroid.messagelist.domain.messages.MessageRepository;
import ru.startandroid.messagelist.domain.usecase.base.CompletableUseCaseWithParams;

public class UpdateMessageFavoriteUseCase extends CompletableUseCaseWithParams<Pair<Long, Boolean>> {

    private final MessageRepository messageRepository;

    @Inject
    public UpdateMessageFavoriteUseCase(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    protected Completable createObservable(Pair<Long, Boolean> parameter) {
        return messageRepository.updateMessageFavorite(parameter.getFirst(), parameter.getSecond());
    }
}
