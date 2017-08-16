package ru.startandroid.messagelist.app.dagger;

import dagger.Component;
import ru.startandroid.messagelist.app.dagger.module.AppModule;
import ru.startandroid.messagelist.app.dagger.module.AppSubComponentsModule;
import ru.startandroid.messagelist.app.dagger.module.DataModule;
import ru.startandroid.messagelist.app.dagger.scope.AppScope;
import ru.startandroid.messagelist.domain.messages.MessageRepository;

@AppScope
@Component(modules = {AppModule.class, AppSubComponentsModule.class, DataModule.class})
public interface AppComponent {
    void injectComponentsHolder(ComponentHolder componentsHolder);
    MessageRepository getMessageRepository();
}