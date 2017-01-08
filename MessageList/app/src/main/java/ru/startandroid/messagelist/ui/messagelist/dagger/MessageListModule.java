package ru.startandroid.messagelist.ui.messagelist.dagger;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.messagelist.data.message.MessageController;
import ru.startandroid.messagelist.events.EventBus;
import ru.startandroid.messagelist.storage.Preferences;
import ru.startandroid.messagelist.ui.messagelist.MessageListContract;
import ru.startandroid.messagelist.ui.messagelist.MessageListPresenter;

@Module
public class MessageListModule {

    @MessageListScope
    @Provides
    MessageListContract.Presenter provideMessageListPresenter(MessageController messageController, EventBus eventBus, Preferences preferences) {
        return new MessageListPresenter(messageController, eventBus, preferences);
    }

}
