package ru.startandroid.messagelist.app.dagger.module;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.messagelist.app.dagger.scope.AppScope;
import ru.startandroid.messagelist.domain.messages.MessageRepository;

@Module
public class MessageRepositoryModule {

    @AppScope
    @Provides
    MessageRepository provideMessageRepository() {
        return Mockito.mock(MessageRepository.class);
    }
}
